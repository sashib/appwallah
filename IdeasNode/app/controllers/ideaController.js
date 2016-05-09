var mongoose = require('mongoose'),
    hashTagHelper = require('../helpers/hashTagHelper'),
    db = require('../../config/database');


mongoose.connect(db.mongoURI[process.env.NODE_ENV], function(err, res) {
  if(err) {
    console.log('Error connecting to the database. ' + err);
  } else {
    console.log('Connected to Database: ' + db.mongoURI[process.env.NODE_ENV]);
  }
});

var Idea = require('../models/idea');

var IDEA_QUERY_LIMIT = 5;
var IDEA_QUERY_SORT_ORDER = {created: -1};

module.exports.addIdea = function(userId, userSource, str, callback) {
  var hashtags = [];

  var idea = new Idea();
  idea.userId = userId;
  idea.userSource = userSource;
  idea.description = str;
  idea.hashtags = hashTagHelper.getHashTags(str);

  idea.save(callback);  
};

module.exports.find = function(userId, userSource, hashTag, callback) {
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
