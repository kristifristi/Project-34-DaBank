const { error } = require('console');

https = require('https');
token = require('./envparse').noobtoken;
const credentials = require("./envparse").credentials;


let data = JSON.stringify({
    "pincode" : "1234",
    "uid" : "FFFFFFFF"
})

let options = {
    port : 443,
    method : 'POST',
    headers : {
        'Content-type': 'application/json',
        'content-length': Buffer.byteLength(data),
        'noob-token': 'f1c26d73-9441-408e-998e-af5274fcba20'
    },
    key : credentials.key,
    cert : credentials.cert,
    //ca : credentials.cert,
    checkServerIdentity: () => { return null; }
};

let req = https.request("https://noob.datalabrotterdam.nl/api/noob/accountinfo?target=IM00IMDB0123456789", options, (response) => {
    console.log(response.statusCode);
    response.on('data', (piece) => {
        console.log(`BODY: ${piece}`);
    });
    response.on('end', () => {
        console.log("that is all");
    });

});

req.on('error', (e) => {
    console.log("man im dead " + e.message);
});

req.write(data);
req.end;

function proxyFun(endpoint, req, res){
    let data = JSON.stringify(req.body);
    let options = {
        port : 443,
        method : 'POST',
        headers : {
            'Content-type': 'application/json',
            'content-length': Buffer.byteLength(data),
            'noob-token': token
        },
        key : credentials.key,
        cert : credentials.cert,
        //ca : credentials.cert,
        checkServerIdentity: () => { return null; }
    };

    let proxyReq = https.request(`https://noob.datalabrotterdam.nl/api/noob/${endpoint}?target=${req.query.target}`, options, (response) => {
    console.log(response.statusCode);
    response.on('data', (piece) => {
        //console.log(`BODY: ${piece}`);
        //i dont care its almost always just one thing
        res.status(response.statusCode).send(piece);
    });
    response.on('end', () => {
        //console.log("that is all");
    });

    proxyReq.on('error', (e) => {
        console.log("man im dead " + e.message);
        res.status(500).send(e.message);
    });

    proxyReq.write(data);
    proxyReq.end;

});

}