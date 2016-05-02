
var request = require('request'),
	messageParser = require('../utils/messageParser'),
    ideas = require('../routes/ideas');

var token = "EAADXCiPJb3MBAO3vDGcguiMv9nzOfq71yrpl3L4PQ7bhXNuaZAioYZBx3d8JIOwOIfyNlqctts5hkqPWsqinMw6jwi4MCHlHkH6QidIYZBeLWR5qY8lGwwYsnXSiSw0BDjiaEbkoWZBwvFj2VM9IWxyl7fJHmyo0OwSK5nn9ygZDZD";

function sendTextMessage(sender, text) {
  messageData = {
    text:text
  }
  request({
    url: 'https://graph.facebook.com/v2.6/me/messages',
    qs: {access_token:token},
    method: 'POST',
    json: {
      recipient: {id:sender},
      message: messageData,
    }
  }, function(error, response, body) {
    if (error) {
      console.log('Error sending message: ', error);
    } else if (response.body.error) {
      console.log('Error: ', response.body.error);
    }
  });
}

function handleNewIdea(sender, text) {
  var callback = function(err) {
    if(err) {
      console.log('error saving idea: ' + err);
      sendTextMessage(sender, "Had trouble adding that idea");
    } else {
      sendTextMessage(sender, "Idea added!");
    }
  }
  ideas.addIdea(text, callback);
}

function handleFind(sender, text) {
  var callback = function (err, ideas) {
    var ideasStr = "";
      if (!err) {
        for (i=0; i < ideas.length; i++) {
          ideasStr += ideas[i].description + '\n\n';
        }
      }
      sendTextMessage(sender, ideasStr);
  };
  ideas.find(messageParser.getFindHashTag(text), callback);   

}

function handleMessageReply(sender, text) {
  if(messageParser.isNewIdea(text)) {
  	handleNewIdea(sender, text);
  } else if (messageParser.isFindByHashTag(text)) {
    handleFind(sender, text);
  }
}

function processMessage(req, res) {
  messaging_events = req.body.entry[0].messaging;
  for (i = 0; i < messaging_events.length; i++) {
    event = req.body.entry[0].messaging[i];
    sender = event.sender.id;
    if (event.message && event.message.text) {
      text = event.message.text;
      // Handle a text message from this sender
      console.log('text sent from messennger is: ' + text);
      handleMessageReply(sender, text);
      //sendTextMessage(sender, "Text received, echo: "+ text.substring(0, 200));
    }
  }
}

exports.handleWebhookGet = function (req, res) {
  console.log('Inside handleWebHookGet');
  if (req.query['hub.verify_token'] === '<validation_token>') {
    res.send(req.query['hub.challenge']);
  }
  res.send('Error, wrong validation token');
}

exports.handleWebhookPost = function (req, res) {
  console.log('Inside handleWebHook');
  console.log('req is: ' + req.body);
  processMessage(req, res);
  res.status(200).send('Success');
}
