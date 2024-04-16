const dbpass = require("./envparse").dbpass;

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

function hasInjection(str){
    return str.includes("\\") || str.includes("\"") || str.includes("--");
}

//function to make doing queries easier.
//parameters: mysql pool, string,  function (results) => any
//results are a json list as return from pool.query
//if an error occured, results will be { error : err(the error that occured)}
function dbquery(usingPool, querystring, callbackfunc){

    usingPool.getConnection((err, Connection) => {
        if(err) {
            console.error("can't connect mariadb", err);
            callbackfunc({'error' : err});
            return;
        }

        usingPool.query(querystring, (err, results) => {
            if (err) {
              console.error("Error querying the database:", err);
              callbackfunc({'error' : err});
              return;
            }
            callbackfunc(results);
            return;
        })
        console.log("successful query of " + querystring);
        Connection.release();
       return;
        
    });
    return;
}

module.exports = { testpool, realpool, dbquery, hasInjection};