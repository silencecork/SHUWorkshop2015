var express = require('express');
var mongodb = require('mongodb');
var ObjectID = require('mongodb').ObjectID;
var app = express();
 
var mongodbURL = 'mongodb://<dbuser>:<dbpassword>@ds039960.mongolab.com:39960/todolist';

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

app.get('/api/query', function(request, response) {
	var collection = myDB.collection('Todos');
	collection.find({}).toArray(function(err, docs) {
		if (err) {
			response.status(406).send(err).end();
		} else {
			response.type('application/json');
			response.status(200).send(docs).end();
		}
	});
});

app.get('/api/insert', function(request, response) {
	var item = {
		title : request.query.title,
		desc : request.query.desc,
		owner : request.query.owner,
		time : request.query.time
	}
	var collection = myDB.collection('Todos');
	collection.insert(item, function(err, result) {
		if (err) {
			response.status(406).send(err).end();
		} else {
			response.type('application/json');
			response.status(200).send(result).end();
		}
	});
});

app.get('/api/delete', function(request, response) {
	var param = {
		_id : new ObjectID(request.query.id)
	}
	console.log(JSON.stringify(param));
	var collection = myDB.collection('Todos');
	collection.remove(param, function(err, result) {
		if (err) {
			console.log('response err' + JSON.stringify(err));
			response.status(406).send(err).end();
		} else {
			response.type('application/json');
			response.status(200).send(result).end();
		}
	});
});

app.listen(5000);


