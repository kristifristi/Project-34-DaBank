let assert = require("assert");

let https = require("https");



const env = require("./envparse");
const noobtoken = env.noobtoken;
const credentials = env.credentials;

let data = JSON.stringify({
    pincode : "1234",
    uid : "FFFFFFFF"
});


let options = {
    port : 8001,
    method : 'POST',
    headers : {
        'Content-type': 'application/json',
        'content-length': Buffer.byteLength(data),
        'noob-token': noobtoken
    },
    key : credentials.key,
    cert : credentials.cert,
    ca : credentials.cert,
    checkServerIdentity: () => { return null; }
};

let Req = https.request("https://145.24.223.74:8001/endme/accountinfo?target=IM00IMDB0123456789", options, (response) => {
    console.log(response.statusCode);
    response.on('error', (err) => {
        console.log("wacky error!");
        console.log(err);
    });
    response.on('data', (piece) => {
        let resdata = JSON.parse(piece.toString())
        assert.equal(resdata.firstname,'J');

    });
    response.on('end', () => {
        console.log("end");
    });
});

Req.on('error', (e) => {
    console.log("man im dead " + e.message);
});
Req.write(data);
Req.end();

let Req2 = https.request("https://145.24.223.74:8001/endme/accountinfo?target=IM00DUMB0123456789", options, (response) => {
    console.log(response.statusCode);
    response.on('error', (err) => {
        console.log("wacky error!");
        console.log(err);
    });
    response.on('data', (piece) => {
        let resdata = JSON.parse(piece.toString())
        assert.equal(resdata.balance,-1);
    });
    response.on('end', () => {
        console.log("end");
    });
});

Req2.on('error', (e) => {
    console.log("man im dead " + e.message);
});
Req2.write(data);
Req2.end();

