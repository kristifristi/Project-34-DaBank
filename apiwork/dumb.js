#!/usr/bin/env node
const fs = require('fs');
const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());
app.use(express.text());

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

        fs.appendFile("/home/gaia/dummy/logfile",string,(err) => {
            if(err){
                console.log(err);
            }
        });
    }
    next();
    return;
});

app.all("/api/accountinfo",(req, res)=> {
    res.status(200).send({
        'firstname' : fs.readFileSync("/home/gaia/dummy/fname.html","utf8"),
        'lastname' : fs.readFileSync("/home/gaia/dummy/lname.html","utf-8"),
        'balance' : -1
    });
});

app.all("/api/*", (req, res) => {
    res.send("OK");
});



app.listen(8420, () => {
    console.log("teehee!")
})