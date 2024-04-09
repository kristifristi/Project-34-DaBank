let password;

const fs = require('fs');

try {
    const data = fs.readFileSync('./.env','utf8');
    password = data;
} catch (e){
    console.error(e);
}

console.log(password);