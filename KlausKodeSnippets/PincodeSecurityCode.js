// needs packages: Nodejs, npm, crypto-js
var SHA256 = require("crypto-js/sha256");

uid = getUid();
pin = getPin();


output = sha256Stringify(uid, pin);
console.log(output);

function sha256Stringify(uid, pin) {
  return(SHA256(uid + pin).toString());
}

function getUid(){
//code here
}

function getPin(){
  //code here
}