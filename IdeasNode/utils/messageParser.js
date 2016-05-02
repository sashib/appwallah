
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

  /*
  while ((match = re.exec(str)) != null){
  	var hashtag = match[0];
  	console.log(hashtag);
  	hashTags.push(hashtag);
  }	
  return hashTags[0];	
  */
}

var isHelp = function(str) {
  var patt = /^help/i;
  if(patt.test(str))
  	return true;
  else 
  	return false;
};

module.exports = {
  isNewIdea: isNewIdea,
  isFindByHashTag: isFindByHashTag,
  getFindHashTag: getFindHashTag,
  isHelp: isHelp
}