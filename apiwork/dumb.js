#!/usr/bin/env node

const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());
app.use(express.text());

app.all("/api/*",(req, res)=> {
    res.status(200).send("OK");
});

app.listen(8420, () => {
    console.log("teehee!")
})