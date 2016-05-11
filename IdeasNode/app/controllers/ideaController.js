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

var ideaController = {};


ideaController.IDEA_QUERY_LIMIT = 5;
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

module.exports = ideaController;
