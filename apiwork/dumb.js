#!/usr/bin/env node

const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());
app.use(express.text());

app.all("/api/accountinfo",(req, res)=> {
    res.status(200).send({
        'firstname' : 'dummy',
        'lastname' : 'dummy',
        'balance' : -1
    });
});

app.all("/api/*", (req, res) => {
    res.send("OK");
})

app.listen(8420, () => {
    console.log("teehee!")
})