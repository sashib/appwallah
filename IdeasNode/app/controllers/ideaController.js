var mongoose = require('mongoose'),
    hashTagHelper = require('../helpers/hashTagHelper'),
    messageHelper = require('../helpers/messageHelper'),
    db = require('../../config/database');

mongoose.connect(db.mongoURI[process.env.NODE_ENV], function(err, res) {
  if(err) {
    console.log('Error connecting to the database. ' + err);
  } else {
    console.log('Connected to Database: ' + db.mongoURI[process.env.NODE_ENV]);
  }
});

var Idea = require('../models/idea');

var ideaController = {};


ideaController.IDEA_QUERY_LIMIT = 2;
ideaController.IDEA_QUERY_SORT_ORDER = {created: -1};

ideaController.addIdea = function(userId, userSource, str, callback) {
  var hashtags = [];

  var idea = new Idea();
  idea.userId = userId;
  idea.userSource = userSource;
  idea.description = str;
  idea.hashtags = hashTagHelper.getHashTags(str);
  idea.save(callback);  
};

ideaController.find = function(userId, userSource, searchTxt, page, callback) {
  if (page == null)
    page = 0;
  
  if (searchTxt != null && searchTxt != '') {
    Idea.find({$text: {$search: searchTxt}})
    .where('userId').equals(userId)
    .where('userSource').equals(userSource)
    .limit(ideaController.IDEA_QUERY_LIMIT)
    .skip(ideaController.IDEA_QUERY_LIMIT * page)
    .sort(ideaController.IDEA_QUERY_SORT_ORDER)
    .exec(callback);
  } else {
    Idea.find()
    .where('userId').equals(userId)
    .where('userSource').equals(userSource)
    .limit(ideaController.IDEA_QUERY_LIMIT)
    .skip(ideaController.IDEA_QUERY_LIMIT * page)
    .sort(ideaController.IDEA_QUERY_SORT_ORDER)
    .exec(callback);
  }

};

ideaController.getTodayIdeas = function(sender, senderSource, cb) {
  var d1 = new Date();
  d1.setUTCHours(0,0,0,0);
  var d2 = new Date();
  d2.setUTCHours(24,0,0,0);

  Idea.count({"created": {"$gte": d1, "$lt": d2}, "userId": sender, "userSource": senderSource}, function(err, c) {
    console.log('Count is ' + c);
    if (c <= 1) {
      txt = messageHelper.ADDED_IDEA_TODAY;
    } else {
      txt = messageHelper.ADDED_IDEAS_TODAY;
    }
    cb(sender, c + txt);
  });

};

/*
ideaController.getStats = function(sender, senderSource, cb) {
  var callback = function(err, ideas) {
    if (!err) {
      var dt = ideas[0].created;
      var dtToday = new Date();
      Idea

    } else {

    }
  }
  Idea.find()
  .where('userId').equals(sender)
  .where('userSource').equals(senderSource)
  .limit(1)
  .exec(callback);
}
*/

module.exports = ideaController;
