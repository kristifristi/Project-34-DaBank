const fs = require('fs');

try {
    const data = fs.readFileSync('./.env.json','utf8');
    env = JSON.parse(data);
} catch (e){
    console.error(e);
}

module.exports = env;