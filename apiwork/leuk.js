#!/usr/bin/env node
const express = require('express');
const cors = require('cors');

const app = express();

app.use(cors());

app.get('/',(req, res) => {
    console.log(req.headers);
    console.log(req.ip);
    res.send("get ip grabbed idiot");
})

app.listen(8080, () =>{
    console.log("server staat klaar");
});