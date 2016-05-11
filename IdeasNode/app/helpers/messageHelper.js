
module.exports.NO_MORE_SEARCH_RESULTS = "That\'s all there is";
module.exports.EMPTY_FIND_RESULTS = "Didn\'t find any ideas";
module.exports.ADDED_IDEA = "Idea added!";
module.exports.ERROR_ADDING_IDEA = "Had trouble adding that idea";
module.exports.HELP = "Hi! I\'m IdeaWallah. \n\n" +
                "If you have an idea, just type it, like \"my #awesome idea\". You can" +
                "use hashtags to categorize your ideas.\n\n" +
                "Type \"find\" to list your latest ideas\n\n" +
                "Type \"find awesome\" to search and list ideas with \"awesome\" in it\n\n" +
                "Type \"tags\" to list your latest used hashtags\n\n" +
                "That\'s it! More to come later. Enjoy!";


module.exports.isNewIdea = function(str) {
  return (!this.isFindByHashTag(str) && !this.isHelp(str));
};

module.exports.isFindByHashTag = function(str) {
  var patt = /^find|^#\w+\b(?! .*)/i;
  return (patt.test(str));
};

module.exports.getFindHashTag = function(str) {
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

module.exports.isHelp = function(str) {
  var patt = /^help$/i;
  return (patt.test(str));
};