var request = require('request'),
	  messageController = require('./messageController');

 // Facebook Page Token for App
module.exports.TOKEN = "EAADXCiPJb3MBAO3vDGcguiMv9nzOfq71yrpl3L4PQ7bhXNuaZAioYZBx3d8JIOwOIfyNlqctts5hkqPWsqinMw6jwi4MCHlHkH6QidIYZBeLWR5qY8lGwwYsnXSiSw0BDjiaEbkoWZBwvFj2VM9IWxyl7fJHmyo0OwSK5nn9ygZDZD";
// Source of userIds
module.exports.USER_SOURCE = "facebook";

module.exports.FB_POST_URL = "https://graph.facebook.com/v2.6/me/messages";

module.exports.buttonTemplatePagingJson = function(sender, text, payloadObj) {
  var json = {};
  json.recipient = { id:sender };
  var buttons = [
    {
       type:"postback",
       title:"More...",
       payload:payloadObj
    }
  ];

  var payload = {
    template_type:"button",
    text: text,
    buttons: buttons
  };
  var attachment = {
    type:"template",
    payload: payload
  };

  json.message = { attachment: attachment };

  return json;
}

module.exports.messageJson = function(sender, text) {
  var json = {};
  json.recipient = { id: sender };
  var messageData = { text: text };
  json.message = messageData;
  return json;
}

module.exports.getReqObj = function(sender, text, payload) {
  var jsonObj;
  if (payload == null) {
    jsonObj = this.messageJson(sender, text);
  } else {
    jsonObj = this.buttonTemplatePagingJson(sender, text, payload);
  }

  var reqObj = {
    url: this.FB_POST_URL,
    qs: {access_token:this.TOKEN},
    method: 'POST',
    json: jsonObj
  };

  return reqObj
}

module.exports.sendTextMessage = function(sender, text, payload) {

  var reqObj = this.getReqObj(sender, text, payload);

  request(reqObj, function(error, response, body) {
    if (error) {
      console.log('Error sending message: ', error);
    } else if (response.body.error) {
      console.log('Error: ', response.body.error);
    }
  });
}

module.exports.processMessage = function(reqBody) {
  //looking at only the first entry
  console.log('in process message: ');
  messaging_events = reqBody.entry[0].messaging;
  for (i = 0; i < messaging_events.length; i++) {
    event = reqBody.entry[0].messaging[i];
    sender = event.sender.id;
    if (event.message && event.message.text) {
      text = event.message.text;
      // Handle a text message from this sender
      console.log('text sent from messennger is: ' + text);
      //handleMessageReply(sender, text);
      messageController.handleMessageReply(sender, this.USER_SOURCE, text, this.sendTextMessage);
    } else if (event.postback) {
      messageController.handlePostback(event.postback.payload, this.sendTextMessage);
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
  console.log('Inside handleWebHookPost ' + req.body);
  this.processMessage(req.body);
  res.status(200).send('Success');
} 



/*
Basic Message
{
    "recipient":{
        "id":"USER_ID"
    }, 
    "message":{
        "text":"hello, world!"
    }

Sample Button Template
  "recipient":{
    "id":"USER_ID"
  },
  "message":{
    "attachment":{
      "type":"template",
      "payload":{
        "template_type":"button",
        "text":"What do you want to do next?",
        "buttons":[
          {
            "type":"web_url",
            "url":"https://petersapparel.parseapp.com",
            "title":"Show Website"
          },
          {
            "type":"postback",
            "title":"Start Chatting",
            "payload":"USER_DEFINED_PAYLOAD"
          }
        ]
      }
    }
  }

Sample Callback POST

{
  "object":"page",
  "entry":[
    {
      "id":"12345",
      "time":1460245674269,
      "messaging":[
        {
          "sender":{
            "id":"11111"
          },
          "recipient":{
            "id":"12345"
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
