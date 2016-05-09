var ideas = require('./ideaController'),
    messageHelper = require('../helpers/messageHelper');


exports.handleNewIdea = function(sender, senderSource, text, cb) {
  var callback = function(err) {
    if(err) {
      cb(sender, messageHelper.ERROR_ADDING_IDEA);
    } else {
      cb(sender, messageHelper.ADDED_IDEA);
    }
  }
  ideas.addIdea(sender, senderSource, text, callback);
}

exports.handleFind = function(sender, senderSource, text, cb) {
  var callback = function (err, ideas) {
    var ideasStr = "";
    if (!err) {
      if (ideas.length <= 0) {
        ideasStr = messageHelper.EMPTY_FIND_RESULTS;
      } else {
        for (i=0; i < ideas.length; i++) {
           ideasStr += ideas[i].description + '\n\n';
        }
      }
    }
    cb(sender, ideasStr);
  };
  ideas.find(sender, senderSource, messageHelper.getFindHashTag(text), callback);   

}

exports.handleMessageReply = function(sender, text) {
  if(this.isNewIdea(text)) {
    this.handleNewIdea(sender, text);
  } else if (this.isFindByHashTag(text)) {
    this.handleFind(sender, text);
  }
}