const crypto = require('crypto');
const {
  createHash,
} = require('node:crypto');

uid = getUid();
pin = getPin();
iban = getIBAN();



const hash = createHash('sha256');

hash.on('readable', () => {
  const data = hash.read();
  if (data) {
    console.log(data.toString('hex'));
  
  }
});

hash.write(uid + pin + iban);
hash.end();

function getUid(){
//code here
return("2344134");
}

function getPin(){
  //code here
return("0000");
}

function getIBAN(){
  return("061198734284");
}