#!/usr/bin/env node

var fs = require('fs');
var http = require('http');
var https = require('https');
const bodyParser = require("body-parser");

const sql = require("./sqlqueries");

const env = require("./envparse");
const noobtoken = env.noobtoken;
const ourIban = env.ourIban;
const credentials = env.credentials;

const SHA256 = require("crypto-js/sha256");
function sha256Stringify(uid, pincode) {
    return(SHA256(uid + pincode).toString());
}


const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
//cargo cult programming
app.use(express.json());
app.use(express.urlencoded());
app.use(express.text());

//imagine actually validating iban
function validateIban(iban){
    const valid = new RegExp("[A-Z]{2}\\d{2}[A-Z]{4}\\d{10}");
    return valid.test(iban);
}

function bankID(iban){
    return iban.substring(4,8);
}

function tokenCheck(headers){
    if(headers['noob-token']){
        if(headers['noob-token'] == noobtoken){
            return true;
        }
    }
    return false;
}

app.all("*", (req,res,next) => {
    if(req.url != "/api/noob/health"){

        let string = `time: ${new Date().toString()}
        ip: ${req.ip}
        url: ${req.url}
        headers:
        ${JSON.stringify(req.headers)}
        body:
        ${req.body}
        ${JSON.stringify(req.body)}

        `

        fs.appendFile("./logfile",string,(err) => {
            if(err){
                console.log(err);
            }
        });
    }
    next();
    return;
});


//blame joram for this one
function proxyFun(endpoint, req, res){
    let data = JSON.stringify(req.body);
    let options = {
        port : 443,
        method : 'POST',
        headers : {
            'Content-type': 'application/json',
            'content-length': Buffer.byteLength(data),
            'noob-token': token
        },
        key : credentials.key,
        cert : credentials.cert,
        //ca : credentials.cert,
        checkServerIdentity: () => { return null; }
    };

    let proxyReq = https.request(`https://noob.datalabrotterdam.nl/api/noob/${endpoint}?target=${req.query.target}`, options, (response) => {
    console.log(response.statusCode);
    response.on('data', (piece) => {
        //i dont care its almost always just one thing
        res.status(response.statusCode).send(piece);
    });
    response.on('end', () => {});

    proxyReq.on('error', (e) => {
        console.log("man im dead " + e.message);
        res.status(500).send(e.message);
    });

    proxyReq.write(data);
    proxyReq.end;

});

}

app.get('/api/noob/health', (req, res) => {
    res.json({"status": "chillin"});
});

function infoApi (req,res) {   
    let body = req.body
    let query = req.query
    if(!tokenCheck(req.headers)){
        res.status(401).send("request not authorised with noob-token");
        return;
    }
    if(!query.target){
        res.status(400).send("missing iban");
        return;
    }
    if(!body.pincode){
        res.status(400).send("missing pincode");
        return;
    }
    if(!body.uid){
        res.status(400).send("missing uid");
        return;
    }

    if(sql.hasInjection(body.uid)){
        console.log("injection attempt! attacker info: ");
        console.log(req.ip);
        console.log(body);
        console.log(req.headers);
        res.status(418).send("you think you're smart don't you?");
        return;
    }

    sql.dbquery(sql.realpool,`select Card.hash as hash, Card.idCard as idCard, 
    Account.accountName as aName, People.firstLetters as fName, People.lastName as lName, 
    Account.balance as balance, Account.IBAN as iban, 
    Card.maxWrongAttempts - Card.wrongAttemptsDone as chances, 
    Card.expiration < CURRENT_DATE as expired, Account.frozen as frozen
    from Card
    join Account on Card.account = idAccount
    join People on Account.owner = idPeople
    where uid =  "${body.uid}"`, (results) => {
        if(results.error){
            res.status(500).send("database error");
            return;
        }

        let result = results[0];
        if(!result){
            res.status(404).send("uid not recognized");
            return;
        }

        realIban = query.target.toUpperCase();
        if(realIban != result.iban){
            res.status(404).send("iban mismatch");
            return;
        }
        if(result.expired){
            res.status(403).send("card expired");
            return;
        }
        if(result.chances <= 0){
            res.status(403).send("card blocked after too many attempts");
            return;
        }

        let genHash = sha256Stringify(body.uid,body.pincode.toString());
        if(genHash != result.hash){
            res.status(401).json({"attempts_remaining" : result.chances});
            sql.dbquery(sql.realpool, `update Card set wrongAttemptsDone = wrongAttemptsDone + 1 where idCard = "${result.idCard}"`, (results) => {});
            return;
        }

        sql.dbquery(sql.realpool, `update Card set wrongAttemptsDone = 0 where idCard = "${result.idCard}"`, (results) => {});
        res.status(200).json({ 
        'firstname' : result.fName,
        'lastname' : result.lName,
        'balance' : result.balance,
        'accountname' : result.aName});
    });
};

app.post('/api/accountinfo', infoApi);
app.post('/endme/accountinfo', (req,res) => {
    if(!tokenCheck(req.headers)){
        res.status(401).send("go away");
        return;
    }
    if(bankID(req.body.target) == "IMDB"){
        infoApi(req, res);
        return;
    }
    proxyFun("accountinfo",req,res);
    return;
});



function withdrawApi (req,res) {
    let body = req.body
    let query = req.query
    if(!tokenCheck(req.headers)){
        res.status(401).send("request not authorised with noob-token");
        return;
    }
    if(!query.target){
        res.status(400).send("missing iban");
        return;
    }
    if(!body.pincode){
        res.status(400).send("missing pincode");
        return;
    }
    if(!body.uid){
        res.status(400).send("missing uid");
        return;
    }
    if(!body.amount){
        res.status(400).send("missing amount");
        return;
    }
    let intAmount = parseInt(body.amount)
    if(!intAmount && intAmount != 0){
        res.status(400).send("amount is not a number");
        return;
    }

    if(sql.hasInjection(body.uid) || sql.hasInjection(query.target)){
        console.log("injection attempt! attacker info: ");
        console.log(req.ip);
        console.log(body);
        console.log(req.headers)
        res.status(418).send("you think you're smart don't you?");
        return;
    }


    sql.dbquery(sql.realpool,`select Card.hash as hash, Card.idCard as idCard,  
        Account.balance as balance, Account.IBAN as iban, 
        Card.maxWrongAttempts - Card.wrongAttemptsDone as chances,
        Card.expiration < CURRENT_DATE as expired, Account.frozen as frozen,
        Account.maxNegativeBalance as maxRed, Account.idAccount as idAccount, 
        Account.dayLimit as dayLimit, 
        (SELECT SUM(amount) FROM TransactionLog WHERE sender = "${query.target}" and CONVERT(\`transactionTime\`,date) = CURRENT_DATE and amount > 0) as spent 
    from Card
    join Account on Card.account = idAccount
    where uid = "${body.uid}"`, (results) => {
        if(results.error){
            res.status(500).send("database error");
            return;
        }

        let result = results[0];
        if(!result){
            res.status(404).send("uid not recognized");
            return;
        }

        let realIban = query.target.toUpperCase();
        if(result.iban != realIban){
            res.status(404).send("iban mismatch");
            return;
        }

        if(result.expired){
            res.status(403).send("card expired");
            return;
        }
        if(result.frozen){
            res.status(403).send("account frozen");
            return;
        }
        if(result.chances <= 0){
            res.status(403).send("card blocked after too many attempts");
            return;
        }
        if(result.maxRed > result.balance - intAmount){
            res.status(412).send("account balance too low");
            return;
        }
        if(result.dayLimit < result.spent + intAmount){
            res.status(403).send("withdrawal would exceed daylimit");
            return;
        }

        let genHash = sha256Stringify(body.uid,body.pincode);
        if(genHash != result.hash){
            res.status(401).json({"attempts_remaining" : result.chances});
            sql.dbquery(sql.realpool, `update Card set wrongAttemptsDone = wrongAttemptsDone + 1 where idCard = ${result.idCard}`, (results) => {});
        }

        //make sure client knows if something went wrong
        const DBpromise = new Promise((resolve, reject) => {
            sql.dbquery(sql.realpool, `insert into TransactionLog value (null,CURRENT_TIME,"${realIban}","${ourIban}",${intAmount},"${req.ip}",${result.idCard})`,(results) => {
            if(results.error){
                reject("database error");
            } else {
                resolve("yippee!");
            }
            return;
        });
        }).then((_) => {
            return new Promise((resolve, reject) => {
                sql.dbquery(sql.realpool, `update Account set balance = balance - ${intAmount} where idAccount = ${result.idAccount}`, (results) => {
                    if(results.error){
                        reject("database error");
                    } else {
                        resolve("yippee!");
                    }
                    return;
                });
            });
        }).then((_) => {
            return new Promise((resolve, reject) => {
                sql.dbquery(sql.realpool, `update Card set wrongAttemptsDone = 0 where idCard = "${result.idCard}"`, (results) => {
                    if(results.error){
                        reject("database error");
                    } else {
                        resolve("yippee!")
                    }
                    return;
                });
            });
        }).then((_) =>{
            res.status(200).send("OK");
        }).catch((err) => {
            res.status(500).send(err);
        });
        return;
    });
}

app.post('/api/withdraw', withdrawApi);
app.post('/endme/withdraw', (req,res) => {
    if(!tokenCheck(req.headers)){
        res.status(401).send("go away");
        return;
    }
    if(bankID(req.body.target) == "IMDB"){
        withdrawApi(req, res);
        return;
    }
    proxyFun("withdraw",req,res);
    return;
});

app.use('/api', (req,res) => {
    res.status(400).send("non-existent endpoint");
});

//var httpServer = http.createServer(app);
var httpsServer = https.createServer(credentials, app);

//httpServer.listen(8100);
httpsServer.listen(8001);
