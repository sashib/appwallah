var ideas = require('./ideaController'),
    messageHelper = require('../helpers/messageHelper');

var messageController = {};

messageController.MAX_CHARS_PER_IDEA = 60;
messageController.MAX_CHARS_PER_TEXT = 320;

messageController.handleNewIdea = function(sender, senderSource, text, cb) {
  var callback = function(err) {
    if(err) {
      cb(sender, messageHelper.ERROR_ADDING_IDEA);
    } else {
      cb(sender, messageHelper.ADDED_IDEA);
    }
  }
  ideas.addIdea(sender, senderSource, text, callback);
}

messageController.getPagingPayload = function(sender, senderSource, searchTxt, page) {
  var payload = {};
  payload.sender = sender;
  payload.senderSource = senderSource;
  payload.page = page + 1;
  payload.searchText = searchTxt;
  return payload;
}

messageController.handleFind = function(sender, senderSource, text, page, cb) {
  var searchText = messageHelper.getFindHashTag(text);
  var payloadNext = messageController.getPagingPayload(sender, senderSource, searchText, page);
  var queryLimit = ideas.IDEA_QUERY_LIMIT;  

  var callback = function (err, ideas) {
    var ideasStr = "";
    var ideasShortStr = "";
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
          var desc = ideas[i].description;
          var descShort = desc;
          if (descShort.length > messageController.MAX_CHARS_PER_IDEA) {
            descShort = descShort.substring(0, messageController.MAX_CHARS_PER_IDEA) + '..';
          }
          ideasStr += desc + '\n\n';
          ideasShortStr += descShort + '\n\n';
        }
        if (ideas.length < queryLimit) {
          payloadNext = null;
        }
        if (ideasStr.length > messageController.MAX_CHARS_PER_TEXT) {
          ideasStr = ideasShortStr;
        }
      }

    } 
    cb(sender, ideasStr, payloadNext);
    
  };
  ideas.find(sender, senderSource, searchText, page, callback);   

}

messageController.handleHelp = function(sender, cb) {
  cb(sender, messageHelper.HELP);
}

messageController.handleMessageReply = function(sender, senderSource, text, cb) {
  if (messageHelper.isFindByHashTag(text)) {
    messageController.handleFind(sender, senderSource, text, 0, cb);
  } else if (messageHelper.isHelp(text)) {
    messageController.handleHelp(sender, cb);
  } else {
    messageController.handleNewIdea(sender, senderSource, text, cb);    
  }
}

messageController.handlePostback = function(payloadStr, cb) {
  console.log("handlePostback: " + payloadStr);
  var payload = JSON.parse(payloadStr);
  messageController.handleFind(payload.sender, payload.senderSource, payload.searchText, payload.page, cb);
}

module.exports = messageController;