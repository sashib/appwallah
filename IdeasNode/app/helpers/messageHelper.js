
exports.EMPTY_FIND_RESULTS = "Didn\'t find any ideas";
exports.ADDED_IDEA = "Idea added!";

exports.ERROR_ADDING_IDEA = "Had trouble adding that idea";


module.exports.isNewIdea = function(str) {
  return (!this.isFindByHashTag(str) && !this.isHelp(str));
};

module.exports.isFindByHashTag = function(str) {
  var patt = /^find|^#\w+\b(?! .*)/i;
  if(patt.test(str))
  	return true;
  else 
  	return false;
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
  var patt = /^help/i;
  if(patt.test(str))
  	return true;
  else 
  	return false;
};