var express = require('express');
var app = express();

app.get('/', function(request, response) {
	response.status(200).send('<html><body><H1>Hello World</H1></body></html>');
	response.end();
});

app.get('/api/test', function(request, response) {
	var ret = {
		msg : 'Hello World',
		status : 0
	}
	response.status(200).send(JSON.stringify(ret));
	response.end();
});

app.listen(5000);


