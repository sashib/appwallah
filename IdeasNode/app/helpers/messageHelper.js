
var messageHelper = {};

messageHelper.NO_MORE_SEARCH_RESULTS = "That\'s all there is";
messageHelper.EMPTY_FIND_RESULTS = "Didn\'t find any ideas";
messageHelper.ADDED_IDEA = "Idea added!";
messageHelper.ERROR_ADDING_IDEA = "Had trouble adding that idea";
messageHelper.HELP = "Hi! I\'m IdeaWallah. Here\'s how I can help:\n\n" +
                "If you have an idea, just type it, like \"my #awesome idea\" and" +
                " I\'ll save it for you. You can " +
                "use hashtags to categorize your ideas to easily find them later.\n\n" +
                "Type \"find\" to list your latest ideas. Or, " +
                "type \"find awesome\" to search and list ideas with \"awesome\" in it.\n\n" +
                //"Type \"tags\" or \"hashtags\" to list your latest used hashtags\n\n" +
                "That\'s it. More to come later. \n\nEnjoy!";


messageHelper.isNewIdea = function(str) {
  return (!messageHelper.isFindByHashTag(str) && !messageHelper.isHelp(str));
};

messageHelper.isFindByHashTag = function(str) {
  var patt = /^find|^#\w+\b(?! .*)/i;
  return (patt.test(str));
};

messageHelper.getFindHashTag = function(str) {
  var hashTags = [];
  //var re = /\#.*?(?=\s|$)/ig;
  var re = /find (.*)/i;
  hashTags = re.exec(str);
  var hashTag = null;
  if (hashTags != null) {
  	hashTag = hashTags[1];
  }
  return hashTag;
};

messageHelper.isHelp = function(str) {
  var patt = /^help$/i;
  return (patt.test(str));
};

module.exports = messageHelper;