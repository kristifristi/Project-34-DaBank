const fs = require('fs');
let envstr = {};
try {
    const data = fs.readFileSync('./.env.json','utf8');
    env = JSON.parse(data);
} catch (e){
    console.error(e);
}

module.exports = env;