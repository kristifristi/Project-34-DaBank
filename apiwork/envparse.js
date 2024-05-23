const fs = require('fs');

try {
    const data = fs.readFileSync('./.env.json','utf8');
    var privateKey  = fs.readFileSync('sslcert/key.pem', 'utf8');
    var certificate = fs.readFileSync('sslcert/server.crt', 'utf8');
    var credentials = {key: privateKey, cert: certificate};
    env = JSON.parse(data);
    env.credentials = credentials
} catch (e){
    console.error(e);
}




module.exports = env;