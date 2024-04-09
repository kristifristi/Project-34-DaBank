const mysql = require("mysql2");


const pool = mysql.createPool({
    host:"localhost",
    user: "main",
    password: "YRpX5t;F+>^TnYqgy3EnOb",
    database:"test"
})

pool.getConnection((err, Connection) => {
    if(err) {
        console.error("can't connect mariadb", err)
        return
    }

    console.log("maria connection succesful!")

    pool.query("SELECT * FROM testable", (err, results) => {
        if (err) {
          console.error("Error querying the database:", err)
          return
        }
        console.log("Query results:", results)
    })
    
    Connection.release()

    
})


  