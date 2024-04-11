#!/usr/bin/env node
const fs = require('fs');
let dbpass = '';
try {
    const data = fs.readFileSync('./.env','utf8');
    dbpass = data;
} catch (e){
    console.error(e);
}

const mysql = require("mysql2");


const realpool = mysql.createPool({
    host:"localhost",
    user: "user",
    password: dbpass,
    database:"erdCompliant"
});

const testpool = mysql.createPool({
    host:"localhost",
    user: "user",
    password: dbpass,
    database:"test"
});

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


//function to make doing queries easier. takes the query and a callback which takes the query result as a parameter
function dbquery(usingPool, querystring, callbackfunc){
    usingPool.getConnection((err, Connection) => {
        if(err) {
            console.error("can't connect mariadb", err)
            return
        }

        usingPool.query(querystring, (err, results) => {
            if (err) {
              console.error("Error querying the database:", err)
              return
            }
            callbackfunc(results);
            return;
        })
        console.log("successful query of " + querystring)
        Connection.release()
       
        
    })
}

function hasInjection(str){
    return str.includes("\\") || str.includes("\"");
}

function validateIban(iban){
    const valid = new RegExp("[A-Z]{2}\\d{2}[A-Z0-9]{1,30}");
    return valid.test(iban);
}

app.get('/api/noob/health', (req, res) => {
    res.json({"status": "chillin"});
});


app.get('/testing/database', (req, res) => {
    dbquery(testpool, "SELECT * FROM testable", (table) => {
        res.json(table);
    })
    
});

app.get('/testing/query', (req, res) => {

    console.log(req.query);
    res.send(req.query);
    
});

app.get('/testing/user/getx', (req, res) => {
    let q = req.query //get a direct reference to query object
    if(q.user){
        dbquery(testpool,`select x from testable where text = "${q.user}"`, (table) => {
            res.json(table[0]);
        })
    } else {
        res.status(400).send("requires 'user' in query!");
    }
});

app.post('/testing/user/add', (req, res) => {
    let q = req.query
    if(q.user){
        dbquery(testpool,`INSERT INTO testable (text,x,y) VALUES ("${q.user}",0,0);`, (table) => {
            res.status(200).send("user added");
        })
    } else {
        res.status(400).send("requires 'user' in query!");
    }
});

app.get('/testing/user/getusers', (req, res) => {
    dbquery(testpool,`select text from testable`, (table) => {
        res.json(table);
    })
});


app.put('/testing/user/setx',(req, res) => {
    let q = req.query
    if(q.user && q.amount){
        dbquery(testpool,`UPDATE testable SET x = ${q.amount} where text = "${q.user}"`, (table) => {
            res.status(200).send(`set x of ${q.user} to ${q.amount}`);
        })
    } else {
        res.status(400).send("requires 'user' and 'amount' in query!");
    }
});


app.get('/testing/sanity', (req,res) => {
    let u = req.query.user;
    if(u){
        if(u.includes("\\") || u.includes("\"")){
            res.status(418).send("bitch");
        } else {
            dbquery(testpool,`select * from testable where text = "${u}"`, (table) => {
                res.status(200).send(table);
            });
        }
    } else {
        res.send(400);
    }
});

app.post('/testing/addcard', (req,res) => {

});

app.post('/noob/api/saldo', (req,res) => {
    let saldoQueries = req.query;
    let saldoHeaders = req.headers;
    let saldoJson = req.body;
    console.log(req.headers);
    console.log(req.body);
    if(!saldoQueries.IBAN){
        console.log('I');
        res.status(400).send("missing IBAN");
        return;
    }
    if(!saldoHeaders['noob-token']){
        console.log('N');
        res.status(400).send("missing NOOB-TOKEN");
        return;
    }
    if(!saldoJson.pin){
        console.log('p');
        res.status(400).send("missing pin");
        return;
    }
    if(!saldoJson.uid){
        console.log('u');
        res.status(400).send("missing uid");
        return;
    }
    let hash = sha256Stringify(saldoJson.uid, saldoJson.pin);
    console.log(`pin: ${saldoJson.pin} uid: ${saldoJson.uid} hash: ${hash}`);
    dbquery(realpool,` select balance as saldo from 
    card join Account on idAccount = card.account
    where card.hash = "${hash}"`, (results) => {
        res.send(results[0]);
    });
    return;
});

app.post("/java-is-trash", (req, res) => {
    console.log(req.body);
    console.log(req.headers);
    res.send(req.body);
    return;
});

app.listen(8100, () =>{
    console.log("AAAAAAAAAAAAAAAA I LIVE");
});