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

module.exports.getPagingPayload = function(sender, senderSource, searchTxt, page) {
  var payload = {};
  payload.sender = sender;
  payload.senderSource = senderSource;
  payload.page = page + 1;
  payload.search_text = text;
}

module.exports.handleFind = function(sender, senderSource, text, page, cb) {
  var searchText = messageHelper.getFindHashTag(text);
  var payloadNext = this.getPagingPayload(sender, senderSource, searchText, page);
  
  var callback = function (err, ideas) {
    var ideasStr = "";
    console.log("ideas len is: " + ideas.length);
    if (!err) {
      if (ideas.length <= 0) {
        if (page > 0)
          ideasStr = messageHelper.NO_MORE_SEARCH_RESULTS;
        else
          ideasStr = messageHelper.EMPTY_FIND_RESULTS;

        payloadNext = null;
      } else {
        for (i=0; i < ideas.length; i++) {
           ideasStr += ideas[i].description + '\n\n';
        }
      }
    } 
    cb(sender, ideasStr, payloadNext);
    
  };
  ideas.find(sender, senderSource, searchText, page, callback);   

}

module.exports.handleHelp = function(sender, cb) {
  cb(sender, messageHelper.HELP);
}

module.exports.handleMessageReply = function(sender, senderSource, text, cb) {
  if (messageHelper.isFindByHashTag(text)) {
    this.handleFind(sender, senderSource, text, 0, cb);
  } else if (messageHelper.isHelp(text)) {
    this.handleHelp(sender, cb);
  } else {
    this.handleNewIdea(sender, senderSource, text, cb);    
  }
}

module.exports.handlePostback = function(payload, cb) {

}