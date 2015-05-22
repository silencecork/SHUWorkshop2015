var express = require('express');
var app = express();

app.get('/', function(request, response) {
	response.status(200).send('<html><body><H1>Hello World</H1></body></html>');
	response.end();
});

app.get('/api/query', function(request, response) {
	response.status(200).send('Query is under construction');
});

app.get('/api/insert', function(request, response) {
	var title = request.query.title;
	var desc = request.query.desc;
	var owner = request.query.owner;
	var time = request.query.time;
	response.status(200).send('Insert title=' + title + ', desc=' + desc + ', owner=' + owner + ', time=' + time);
});

app.get('/api/delete', function(request, response) {
	var id = request.query.id;
	response.status(200).send('Remove id=' + id);
});

app.listen(5000);


