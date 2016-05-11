var ideas = require('./ideaController'),
    messageHelper = require('../helpers/messageHelper');


module.exports.handleNewIdea = function(sender, senderSource, text, cb) {
  var callback = function(err) {
    if(err) {
      cb(sender, messageHelper.ERROR_ADDING_IDEA);
    } else {
      cb(sender, messageHelper.ADDED_IDEA);
    }
  }
  ideas.addIdea(sender, senderSource, text, callback);
}

module.exports.handleFind = function(sender, senderSource, text, cb) {
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

module.exports.handleHelp = function(sender, cb) {
  cb(sender, messageHelper.HELP);
}

module.exports.handleMessageReply = function(sender, senderSource, text, cb) {
  if (messageHelper.isFindByHashTag(text)) {
    this.handleFind(sender, senderSource, text, cb);
  } else if (messageHelper.isHelp(text)) {
    this.handleHelp(sender, cb);
  } else {
    this.handleNewIdea(sender, senderSource, text, cb);    
  }
}