var express = require('express');
var mongodb = require('mongodb');
var app = express();

var mongodbURL = 'mongodb://justin:justin12345@ds053190.mongolab.com:53190/test';

var myDB;
mongodb.MongoClient.connect(mongodbURL, function(err, db) {
	if (err) {
		console.log(err);
	} else {
		myDB = db;
		console.log('connection success');
	}
});

app.get('/', function(request, response) {
	response.status(200).send('<html><body><H1>Hello World</H1></body></html>');
	response.end();
});

app.get('/api/test', function(request, response) {
	var collection = myDB.collection('my_data');
	collection.find({}).toArray(function(err, docs) {
		if (err) {
			response.status(406).send(err);
			response.end();
		} else {
			response.type('application/json');
			response.status(200).send(docs);
			response.end();
		}
	})
});

app.listen(5000);


