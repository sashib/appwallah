var request = require('request'),
    facebookConfig = require('../../config/facebook'),
	  messageController = require('./messageController');

var facebookController = {};

facebookController.buttonTemplatePagingJson = function(sender, text, payloadObj) {
  var json = {};
  json.recipient = { id:sender };
  var buttons = [
    {
       type:"postback",
       title:"More...",
       payload:JSON.stringify(payloadObj)
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

facebookController.messageJson = function(sender, text) {
  var json = {};
  json.recipient = { id: sender };
  var messageData = { text: text };
  json.message = messageData;
  return json;
}

facebookController.getReqObj = function(sender, text, payload) {
  var jsonObj;
  if (payload == null) {
    jsonObj = facebookController.messageJson(sender, text);
  } else {
    jsonObj = facebookController.buttonTemplatePagingJson(sender, text, payload);
  }

  var reqObj = {
    url: facebookConfig.postUrl,
    qs: {access_token:facebookConfig.pageToken},
    method: 'POST',
    json: jsonObj
  };

  return reqObj
}

facebookController.sendTextMessage = function(sender, text, payload) {

  console.log("in sendTextMessage, sender: " + sender + ", payload: " + JSON.stringify(payload));
  var reqObj = facebookController.getReqObj(sender, text, payload);

  request(reqObj, function(error, response, body) {
    if (error) {
      console.log('Error sending message: ', error);
    } else if (response.body.error) {
      console.log('Error: ', response.body.error);
    }
  });
}

facebookController.processMessage = function(reqBody) {
  //looking at only the first entry
  console.log('processmessage');
  messaging_events = reqBody.entry[0].messaging;
  for (i = 0; i < messaging_events.length; i++) {
    event = reqBody.entry[0].messaging[i];
    sender = event.sender.id;
    if (event.message && event.message.text) {
      text = event.message.text;
      messageController.handleMessageReply(sender, facebookConfig.source, text, facebookController.sendTextMessage);
    } else if (event.postback) {
      messageController.handlePostback(event.postback.payload, facebookController.sendTextMessage);
    }
  }
}

facebookController.handleWebhookGet = function (req, res) {
  console.log('Inside handleWebHookGet');
  if (req.query['hub.verify_token'] === '<validation_token>') {
    res.send(req.query['hub.challenge']);
  }
  res.send('Error, wrong validation token');
}

facebookController.handleWebhookPost = function (req, res) {

  console.log('in handleWebhookPost: ' + JSON.stringify(req.body));
  facebookController.processMessage(req.body);
  res.status(200).send('Success');
} 

module.exports = facebookController;

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

Sample Callback for Postback
{
  "object":"page",
  "entry":[
    {
      "id":PAGE_ID,
      "time":1458692752478,
      "messaging":[
        {
          "sender":{
            "id":USER_ID
          },
          "recipient":{
            "id":PAGE_ID
          },
          "timestamp":1458692752478,
          "postback":{
            "payload":"USER_DEFINED_PAYLOAD"
          }
        }
      ]
    }
  ]
}

*/
