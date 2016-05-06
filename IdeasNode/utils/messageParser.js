var ideas = require('../routes/ideas'),
    messages = require('./messages');


var isNewIdea = function(str) {
  return (!isFindByHashTag(str) && !isHelp(str));
};

var isFindByHashTag = function(str) {
  var patt = /^find|^#\w+\b(?! .*)/i;
  if(patt.test(str))
  	return true;
  else 
  	return false;
};

var getFindHashTag = function(str) {
  var hashTags = [];
  //var re = /\#.*?(?=\s|$)/ig;
  var re = /find (.*)/i;
  hashTags = re.exec(str);
  var hashTag = null;
  if (hashTags != null) {
  	hashTag = hashTags[1];
  }
  return hashTag;
}

var isHelp = function(str) {
  var patt = /^help/i;
  if(patt.test(str))
  	return true;
  else 
  	return false;
};


var handleNewIdea = function(sender, senderSource, text, cb) {
  var callback = function(err) {
    if(err) {
      console.log('error saving idea: ' + err);
      cb(sender, messages.ERROR_ADDING_IDEA);
    } else {
      console.log('saved new idea, will send success msg ' + messages.ADDED_IDEA);
      cb(sender, messages.ADDED_IDEA);
    }
  }
  ideas.addIdea(sender, senderSource, text, callback);
}

var handleFind = function(sender, senderSource, text, cb) {
  var callback = function (err, ideas) {
    var ideasStr = "";
      if (!err) {
        if (ideas.length <= 0) {
          ideasStr = messages.EMPTY_FIND_RESULTS;
        } else {
          for (i=0; i < ideas.length; i++) {
            ideasStr += ideas[i].description + '\n\n';
          }
        }
      }
      cb(sender, ideasStr);
  };
  ideas.find(sender, senderSource, getFindHashTag(text), callback);   

}

var handleMessageReply = function(sender, text) {
  if(messageParser.isNewIdea(text)) {
    handleNewIdea(sender, text);
  } else if (messageParser.isFindByHashTag(text)) {
    handleFind(sender, text);
  }
}

module.exports = {
  isNewIdea: isNewIdea,
  isFindByHashTag: isFindByHashTag,
  getFindHashTag: getFindHashTag,
  isHelp: isHelp,
  handleNewIdea: handleNewIdea,
  handleFind: handleFind
}