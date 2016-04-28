var mongoose = require('mongoose');
var HashTagParser = require('../utils/hashTagParser');

//dev
//mongoose.connect('mongodb://localhost/ideas');

//prod
mongoose.connect('mongodb://db:ideasd3v@ds021691.mlab.com:21691/ideasdb')

var Idea = require('../models/idea');

var IDEA_QUERY_LIMIT = 5;
var IDEA_QUERY_SORT_ORDER = {created: -1};

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