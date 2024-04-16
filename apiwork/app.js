#!/usr/bin/env node
const sql = require("./queries");

const noobtoken = require("./envparse").noobtoken;
const ourIban = require("./envparse").ourIban;

const SHA256 = require("crypto-js/sha256");
function sha256Stringify(uid, pin) {
    return(SHA256(uid + pin).toString());
}


const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());
app.use(express.urlencoded());
app.use(express.raw());

const bodyParser = require("body-parser");
const rawparser = bodyParser.raw();
const textparser = bodyParser.text();
//welcome to callback hell!


function validateIban(iban){
    const valid = new RegExp("[A-Z]{2}\\d{2}[A-Z]{4}\\d{10}");
    return valid.test(iban);
}

app.get('/api/noob/health', (req, res) => {
    res.json({"status": "chillin"});
});

function infoApi (req,res) {   
    let queries = req.query
    if(!queries.iban){
        res.status(400).send("missing iban");
        return;
    }
    if(!queries.pin){
        res.status(400).send("missing pin");
        return;
    }
    if(!queries.uid){
        res.status(400).send("missing uid");
        return;
    }

    if(sql.hasInjection(queries.iban) || sql.hasInjection(queries.pin) || sql.hasInjection(queries.uid)){
        console.log("injection attempt! attacker info: ");
        console.log(req.ip);
        console.log(queries);
        console.log(req.headers)
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
    where uid =  "${queries.uid}"`, (results) => {
        if(results.error){
            res.status(500).send("database error");
            return;
        }

        let result = results[0];
        if(!result){
            res.status(404).send("uid not recognized");
            return;
        }

        realIban = queries.iban.toUpperCase();
        if(realIban != result.iban){
            res.status(404).send("iban mismatch");
            return;
        }
        if(result.expired){
            res.status(403).send("card expired");
        }
        if(result.chances <= 0){
            res.status(403).send("card blocked after too many attempts");
        }

        let genHash = sha256Stringify(queries.uid,queries.pin.toString());
        if(genHash != result.hash){
            res.status(401).json({"attempts_remaining" : result.chances});
            sql.dbquery(sql.realpool, `update Card set wrongAttemptsDone = wrongAttemptsDone + 1 where idCard = "${result.idCard}"`, (results) => {});
            return;
        }

        res.status(200).json({ 
            'firstname' : result.fName,
            'lastname' : result.lName,
        'balance' : result.balance,
        'accountname' : result.aName});
    });
};

app.get('/api/noob/accountinfo', infoApi);
app.get('/api/accountinfo', infoApi);

function withdrawApi (req,res) {
    let queries = req.query
    if(!queries.iban){
        res.status(400).send("missing iban");
        return;
    }
    if(!queries.pin){
        res.status(400).send("missing pin");
        return;
    }
    if(!queries.uid){
        res.status(400).send("missing uid");
        return;
    }
    if(!req.body.amount){
        res.status(400).send("missing amount");
    }

    if(sql.hasInjection(queries.iban) || sql.hasInjection(queries.pin) || sql.hasInjection(queries.uid)){
        console.log("injection attempt! attacker info: ");
        console.log(req.ip);
        console.log(queries);
        console.log(req.headers)
        res.status(418).send("you think you're smart don't you?");
        return;
    }
    sql.dbquery(sql.realpool,`select Card.hash as hash, Card.idCard as idCard,  
        Account.balance as balance, Account.IBAN as iban, 
        Card.maxWrongAttempts - Card.wrongAttemptsDone as chances,
        Card.expiration < CURRENT_DATE as expired, Account.frozen as frozen,
        Account.maxNegativeBalance as maxRed, Account.idAccount as idAccount
    from Card
    join Account on Card.account = idAccount
    where uid = "${queries.uid}"`, (results) => {
        if(results.error){
            res.status(500).send("database error");
            return;
        }

        let result = results[0];
        console.log(results);
        if(!result){
            res.status(404).send("uid not recognized");
            return;
        }

        queries.iban = queries.iban.toUpperCase();
        if(result.iban != queries.iban){
            res.status(404).send("iban mismatch");
            return;
        }

        if(result.expired){
            res.status(403).send("card expired");
        }
        if(result.frozen){
            res.status(403).send("account frozen");
        }
        if(result.chances <= 0){
            res.status(403).send("card blocked after too many attempts");
        }
        if(result.maxRed >= result.balance - req.body.amount){
            res.status(412).send("account balance too low");
        }

        let genHash = sha256Stringify(queries.uid,queries.pin);
        if(genHash != result.hash){
            res.status(401).json({"attempts_remaining" : result.chances});
            sql.dbquery(sql.realpool, `update Card set wrongAttemptsDone = wrongAttemptsDone + 1 where idCard = ${result.idCard}`, (results) => {});
            return;
        }
        sql.dbquery(sql.realpool, `insert into TransactionLog value
        (null,CURRENT_TIME,"${result.iban}","${ourIban}",${req.body.amount},"${req.ip}",${result.idCard})`,(results) => {
            console.log(results);
        });
        sql.dbquery(sql.realpool, `update Account set balance = balance - ${req.body.amount} where idAccount = ${result.idAccount}`, (results) => {});
        res.status(200).send();
        return;
    });
}

app.post('/api/noob/withdraw', withdrawApi);
app.post('/api/withdraw', withdrawApi);


app.listen(8100, () =>{
    console.log("AAAAAAAAAAAAAAAA I LIVE");
});