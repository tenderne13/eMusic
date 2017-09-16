function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function getRootPath(){
	//pathName:--->   mbuy/user/login.action
	var pathName = window.location.pathname.substring(1);
	//webName:--->mbuy
	var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
	//return:--->http://localhost:9999/mbuy/
	return window.location.protocol + '//' + window.location.host + '/'+ webName + '/';
}

function _create_secret_key(size) {
	var result = [];
	var choice = '012345679abcdef'.split('');
	for (var i=0; i<size; i++) {
		var index = Math.floor(Math.random() * choice.length);
		result.push(choice[index]);
	}
	return result.join('');
}


function _aes_encrypt(text, sec_key) {
	var pad = 16 - text.length % 16;
	for (var i=0; i<pad; i++) {
		text = text + String.fromCharCode(pad);
	}
	var key = aesjs.util.convertStringToBytes(sec_key);
	// The initialization vector, which must be 16 bytes
	var iv = aesjs.util.convertStringToBytes("0102030405060708");
	var textBytes = aesjs.util.convertStringToBytes(text);
	var aesCbc = new aesjs.ModeOfOperation.cbc(key, iv);
	var cipherArray = [];
	while(textBytes.length != 0) {
		var block = aesCbc.encrypt(textBytes.slice(0, 16));
		Array.prototype.push.apply(cipherArray,block);
		textBytes = textBytes.slice(16);
	}
	var ciphertext = '';
	for (var i=0; i<cipherArray.length; i++) {
		ciphertext = ciphertext + String.fromCharCode(cipherArray[i]);
	}
	ciphertext = btoa(ciphertext)
	return ciphertext;
}

function hexify(text) {
	return text.split('').map(function(x){return x.charCodeAt(0).toString(16)}).join('');
}

function zfill(num, size) {
	var s = num+"";
	while (s.length < size) s = "0" + s;
	return s;
}


function expmod( base, exp, mymod ) {
	if ( equalsInt(exp, 0) == 1) return int2bigInt(1,10);
	if ( equalsInt(mod(exp, int2bigInt(2,10) ), 0) ) {
		var newexp = dup(exp);
		rightShift_(newexp,1);
		var result = powMod(expmod( base, newexp, mymod), [2,0], mymod);
		return result;
	}
	else {
		var result = mod(mult(expmod( base, sub(exp, int2bigInt(1,10)), mymod), base), mymod);
		return result;
	}
}

function _rsa_encrypt(text, pubKey, modulus) {
	text = text.split('').reverse().join('');
	var base = str2bigInt(hexify(text), 16);
	var exp = str2bigInt(pubKey, 16);
	var mod = str2bigInt(modulus, 16);
	var bigNumber = expmod(base, exp, mod);
	var rs = bigInt2str(bigNumber, 16);
	return zfill(rs, 256).toLowerCase();
}

function _encrypted_request(text) {
	var modulus = '00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b72' +
		'5152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbd' +
		'a92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe48' +
		'75d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7';
	var nonce = '0CoJUm6Qyw8W8jud';
	var pubKey = '010001';
	text = JSON.stringify(text);
	var sec_key = _create_secret_key(16);
	var enc_text = _aes_encrypt(_aes_encrypt(text, nonce), sec_key);
	var enc_sec_key = _rsa_encrypt(sec_key, pubKey, modulus);
	var data = {
		'params': enc_text,
		'encSecKey': enc_sec_key
	};

	return data;
}