var mongoose = require('mongoose');
var HashTagParser = require('../utils/hashTagParser');
var config = require('../config');


mongoose.connect(config.mongoURI[process.env.NODE_ENV], function(err, res) {
  if(err) {
    console.log('Error connecting to the database. ' + err);
  } else {
    console.log('Connected to Database: ' + config.mongoURI[process.env.NODE_ENV]);
  }
});

var Idea = require('../models/idea');

var IDEA_QUERY_LIMIT = 5;
var IDEA_QUERY_SORT_ORDER = {created: -1};

exports.addIdea = function(userId, userSource, str, callback) {
  var hashtags = [];

  //console.log('New idea: ' + str);

  var hashTagParser = new HashTagParser();
  var idea = new Idea();
  idea.userId = userId;
  idea.userSource = userSource;
  idea.description = str;
  idea.hashtags = hashTagParser.getHashTags(str);

  idea.save(callback);  
};

exports.find = function(userId, userSource, hashTag, callback) {
  if (hashTag != null) {
    Idea.find({$text: {$search: hashTag}})
    .where('userId').equals(userId)
    .where('userSource').equals(userSource)
    .limit(IDEA_QUERY_LIMIT)
    .sort(IDEA_QUERY_SORT_ORDER)
    .exec(callback);
  } else {
    Idea.find()
    .where('userId').equals(userId)
    .where('userSource').equals(userSource)
    .limit(IDEA_QUERY_LIMIT)
    .sort(IDEA_QUERY_SORT_ORDER)
    .exec(callback);
  }

};


/*
exports.findAll = function(req, res) {
  var callback = function (err, ideas) {
      if (err) 
      	res.send(err);
      res.json(ideas);
    };

  var q_hashtags = req.query.hashtags;
  console.log("finding all Ideas: " + q_hashtags);
  if (q_hashtags != null) {
    Idea.find({$text: {$search: q_hashtags}})
    .limit(IDEA_QUERY_LIMIT)
    .sort(IDEA_QUERY_SORT_ORDER)
    .exec(callback);

  } else {
	Idea.find()
	.limit(IDEA_QUERY_LIMIT)
	.sort(IDEA_QUERY_SORT_ORDER)
	.exec(callback);

  }
};

exports.addIdea = function(req, res) {
  var str = req.body.description;
  var hashtags = [];

  console.log(str);

  var hashTagParser = new HashTagParser();
  var idea = new Idea();
  idea.description = str;
  idea.hashtags = hashTagParser.getHashTags(str);

  idea.save(function(err) {
    if(err)
      res.send(err);
    res.json({message: idea});
  })
  
};
*/