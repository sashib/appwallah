var request = require('request'),
	  messageHelper = require('../helpers/messageHelper');

 // Facebook Page Token for App
var TOKEN = "EAADXCiPJb3MBAO3vDGcguiMv9nzOfq71yrpl3L4PQ7bhXNuaZAioYZBx3d8JIOwOIfyNlqctts5hkqPWsqinMw6jwi4MCHlHkH6QidIYZBeLWR5qY8lGwwYsnXSiSw0BDjiaEbkoWZBwvFj2VM9IWxyl7fJHmyo0OwSK5nn9ygZDZD";
// Source of userIds
var USER_SOURCE = "facebook";


module.exports.sendTextMessage = function(sender, text) {
  messageData = {
    text:text
  }
  request({
    url: 'https://graph.facebook.com/v2.6/me/messages',
    qs: {access_token:TOKEN},
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

module.exports.processMessage = function(req, res) {
  //looking at only the first entry
  messaging_events = req.body.entry[0].messaging;
  for (i = 0; i < messaging_events.length; i++) {
    event = req.body.entry[0].messaging[i];
    sender = event.sender.id;
    if (event.message && event.message.text) {
      text = event.message.text;
      // Handle a text message from this sender
      console.log('text sent from messennger is: ' + text);
      //handleMessageReply(sender, text);
      messageHelper.handleMessageReply(sender, text, this.sendTextMessage);
    }
  }
}

module.exports.handleWebhookGet = function (req, res) {
  console.log('Inside handleWebHookGet');
  if (req.query['hub.verify_token'] === '<validation_token>') {
    res.send(req.query['hub.challenge']);
  }
  res.send('Error, wrong validation token');
}

module.exports.handleWebhookPost = function (req, res) {
  console.log('Inside handleWebHook');
  this.processMessage(req, res);
  //res.status(200).send('Success');
  res.status(200).send('Success');
  //console.log(ss);
} 



/*
Sample Callback POST

{
  "object":"page",
  "entry":[
    {
      "id":"PAGE_ID",
      "time":1460245674269,
      "messaging":[
        {
          "sender":{
            "id":"USER_ID"
          },
          "recipient":{
            "id":"PAGE_ID"
          },
          "timestamp":1460245672080,
          "message":{
            "mid":"mid.1460245671959:dad2ec9421b03d6f78",
            "seq":216,
            "text":"hello"
          }
        }
      ]
    }
  ]
}

*/