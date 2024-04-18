const fs = require('fs');
try {
    const data = fs.readFileSync('./.env','utf8');
    dbpass = data;
} catch (e){
    console.error(e);
}

const mysql = require("mysql2");


const pool = mysql.createPool({
    host:"localhost",
    user: "user",
    password: dbpass,
    database:"test"
});

const express = require('express');
const cors = require('cors');

const app = express();

//welcome to callback hell!


//function to make doing queries easier. takes the query and a callback which takes the query result as a parameter
function dbquery(querystring, callbackfunc){
    pool.getConnection((err, Connection) => {
        if(err) {
            console.error("can't connect mariadb", err)
            return
        }

        pool.query(querystring, (err, results) => {
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

function validateIban(iban){
    const valid = new RegExp("[A-Z]{2}\\d{2}[A-Z0-9]{1,30}");
    return valid.test(iban);
}

app.use(cors());

app.get('/api/noob/health', (req, res) => {
    console.log('dokter is langsgekomen');
    res.json({"status": "OK"});
});


app.get('/testing/database', (req, res) => {
    dbquery("SELECT * FROM testable", (table) => {
        res.json(table);
    })
    
});

app.get('/testing/query', (req, res) => {

    console.log(req.query);
    res.send(req.query);
    
});

app.get('/testing/user/getx', (req, res) => {
    q = req.query //get a direct reference to query object
    if(q.user){
        dbquery(`select x from testable where text = "${q.user}"`, (table) => {
            res.json(table[0]);
        })
    } else {
        res.status(400).send("requires 'user' in query!");
    }
});

app.post('/testing/user/add', (req, res) => {
    q = req.query
    if(q.user){
        dbquery(`INSERT INTO testable (text,x,y) VALUES ("${q.user}",0,0);`, (table) => {
            res.status(200).send("user added");
        })
    } else {
        res.status(400).send("requires 'user' in query!");
    }
});

app.get('/testing/user/getusers', (req, res) => {
    dbquery(`select text from testable`, (table) => {
        res.json(table);
    })
});


app.put('/testing/user/setx',(req, res) => {
    q = req.query
    if(q.user && q.amount){
        dbquery(`UPDATE testable SET x = ${q.amount} where text = "${q.user}"`, (table) => {
            res.status(200).send(`set x of ${q.user} to ${q.amount}`);
        })
    } else {
        res.status(400).send("requires 'user' and 'amount' in query!");
    }
});

app.post('/testing/poster', (req, res) => {
    res.send("good job");
});

app.get(('/testing/sanity'), (req,res) => {
    u = req.query.user;
    if(u){
        if(u.includes("\\") || u.includes("\"")){
            res.status(418).send("bitch");
        } else {
            dbquery(`select * from testable where text = "${u}"`, (table) => {
                res.status(200).send(table);
            });
        }
    } else {
        res.send(400);
    }
});


app.listen(8100, () =>{
    console.log("AAAAAAAAAAAAAAAA I LIVE");
});

