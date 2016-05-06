var request = require('request'),
    ideas = require('../routes/ideas');

var USER_SOURCE = "development";


function handleFind(sender, text) {
}


exports.findAll = function (req, res) {
  var sender = req.query.sender;
  if (sender== null)
    res.status(401).send("unauthorized");

  var hashtags = req.query.find;
  var callback = function (err, ideas) {
      if (err) 
        res.send(err);
      res.json(ideas);
    };

  ideas.find(sender, USER_SOURCE, hashtags, callback);   
}

exports.addIdea = function (req, res) {
  //console.log('Inside handlePost');
  var sender = req.body.sender;

  if (sender== null)
    res.status(401).send("unauthorized");

  var text = req.body.description;
  //console.log(text);

  ideas.addIdea(sender, USER_SOURCE, text, function(err, newidea) {
    if(err)
      res.send(err);
    res.json({success: newidea});
  });
}
