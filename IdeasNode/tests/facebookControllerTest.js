process.env.NODE_ENV = 'test';

var should = require('chai').should(),
    sinon = require('sinon'),
    req = require('Request'),
    proxyquire = require('proxyquire'),
    messageHelper = require('../app/helpers/messageHelper'),
    facebook = require('../app/controllers/facebookController'),
    fbConfig = require('../config/facebook');

var sampleFbPostBody = {
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
};

var sampleFbPostbackBody = {
  "object":"page",
  "entry":[
    {
      "id":'12345',
      "time":1458692752478,
      "messaging":[
        {
          "sender":{
            "id":'11111'
          },
          "recipient":{
            "id":'12345'
          },
          "timestamp":1458692752478,
          "postback":{
            "payload":"USER_DEFINED_PAYLOAD"
          }
        }
      ]
    }
  ]
};
        
describe('facebook', function() {

/*
  describe('processMessage()', function () {    
    it('should parse facebook POST and call sendTextMessage', function(done) {
      var sendMessageStub = sinon.stub(facebook, 'sendTextMessage', function(sender, text) {
        console.log('sendmsgstub called: ' + sender);
        sender.should.equal('11111');
        text.should.equal(messageHelper.ADDED_IDEA);
        done();
      });
      facebook.processMessage(sampleFbPostBody)
    });
  });
*/

  describe('processMessage()', function () {    
    it('should parse facebook POST, call messageController.handleReply and receive callback to sendTextMessage', function(done) {      
      var messageContStub = {};
      messageContStub.handleMessageReply = function(sender, senderSource, text, cb) {
        cb(sender, text);
      };
      var facebookCont = proxyquire('../app/controllers/facebookController', {'./messageController':messageContStub});
      var sendMessageStub = sinon.stub(facebookCont, 'sendTextMessage', function(sender, text) {
        console.log('sendmsgstub called: ' + sender);
        sender.should.equal('11111');
        text.should.equal('hello');
        done();
      });
      facebookCont.processMessage(sampleFbPostBody);
    });
    it('should parse postback POST and call messageController.handlePostback and receive callback to sendTextMessage', function(done) {
      var messageContStub = {};
      messageContStub.handlePostback = function(payload, cb) {
        cb(sender, text, payload);
      };
      var facebookCont = proxyquire('../app/controllers/facebookController', {'./messageController':messageContStub});
      var sendMessageStub = sinon.stub(facebookCont, 'sendTextMessage', function(sender, text, payload) {
        console.log('sendmsgstub called: ' + sender);
        sender.should.equal('11111');
        text.should.equal('hello');
        done();
      });
      facebookCont.processMessage(sampleFbPostbackBody)      
    });
  });

  describe('handleWebhookPost()', function() {
    it('should call processMessage with req.body, and return status 200', function(done) {
      var res = { status:function(statusCode) {} };
      var sendObj = { send:function(msg) {} };   

      var resStatusSendStub = sinon.stub(sendObj, "send");
      var resStatusStub = sinon.stub(res, "status", function(statusCode) {
        return sendObj;
      });
      
      var stub = sinon.stub(facebook, "processMessage");

      facebook.handleWebhookPost(req, res);

      stub.calledWith(req.body).should.be.ok;
      resStatusStub.calledWith(200).should.be.ok;
      resStatusSendStub.calledWith('Success').should.be.ok;
      
      stub.restore();
      done();      
    });
  });

  describe('sendTextMessage()', function() {
    it('should make a standard message POST request', function(done) {
      var sender = "11111";
      var text = messageHelper.ADDED_IDEA;

      var request = function(obj, cb) {
        obj.url.should.equal(fbConfig.postUrl);
        obj.method.should.equal("POST");
        obj.qs.access_token.should.equal(fbConfig.pageToken);
        obj.json.recipient.id.should.equal(sender);
        obj.json.message.text.should.equal(text);
        done();
      };
      var facebookCont = proxyquire('../app/controllers/facebookController', {'request':request});
      
      facebookCont.sendTextMessage(sender, text);
    });
    it('should make a buttonTemplate POST request', function(done) {
      var sender = "11111";
      var payload = {sender:'11111', senderSource:'development', searchText:null, page:1};
      var text = "my ideas";
      var templateJson = facebook.buttonTemplatePagingJson(sender, text, payload);

      var request = function(obj, cb) {
        obj.url.should.equal(fbConfig.postUrl);
        obj.method.should.equal("POST");
        obj.qs.access_token.should.equal(fbConfig.pageToken);
        obj.json.recipient.id.should.equal(sender);
        obj.json.message.attachment.payload.buttons[0].payload.should.equal(JSON.stringify(payload));
        done();
      };
      var facebookCont = proxyquire('../app/controllers/facebookController', {'request':request});
      
      facebookCont.sendTextMessage(sender, text, payload);

    });    
  });
  describe('handleSendCampaign', function() {
    it('should return correct json', function(done) {
      var req = {
        body: {
          title: "title 1",
          subtitle: "subtitle 1",
          imgUrl: "imgurl",
          userId: "1234",
          buttonTitle: "first button",
          buttonUrl: "first url",
          buttonTitle2: "second button",
          buttonUrl2: "second url"
        }
      };
      var res = { status:function(statusCode) {} };
      var sendObj = { send:function(msg) {} };   

      var resStatusSendStub = sinon.stub(sendObj, "send");
      var resStatusStub = sinon.stub(res, "status", function(statusCode) {
        return sendObj;
      });
      
      
      var expectedJson = {
        "recipient":{
          "id":"1234"
        },
        "message":{
          "attachment":{
            "type":"template",
            "payload":{
              "template_type":"generic",
              "elements":[
                {
                  "title":"title 1",
                  "image_url":"imgurl",
                  "subtitle":"subtitle 1",
                  "buttons":[
                    {
                      "type":"web_url",
                      "url":"first url",
                      "title":"first button"
                    },
                    {
                      "type":"web_url",
                      "url":"second url",
                      "title":"second button"
                    }              
                  ]
                }
              ]
            }
          }
        }
      };
      var stub = sinon.stub(facebook, "sendCampaignMessage");

      facebook.handleSendCampaign(req, res);

      stub.calledWith(expectedJson).should.be.ok;
      
      resStatusStub.calledWith(200).should.be.ok;
      resStatusSendStub.calledWith('Success').should.be.ok;

      stub.restore();

      done();

    });

  });
  describe('sendCampaignMessage()', function() {
    it('should make a genericTemplate POST request', function(done) {
      var jsonObj = {
        "recipient":{
          "id":"1234"
        },
        "message":{
          "attachment":{
            "type":"template",
            "payload":{
              "template_type":"generic",
              "elements":[
                {
                  "title":"title 1",
                  "image_url":"imgurl",
                  "subtitle":"subtitle 1",
                  "buttons":[
                    {
                      "type":"web_url",
                      "url":"first url",
                      "title":"first button"
                    },
                    {
                      "type":"web_url",
                      "url":"second url",
                      "title":"second button"
                    }              
                  ]
                }
              ]
            }
          }
        }
      };

      var request = function(obj, cb) {
        obj.url.should.equal(fbConfig.postUrl);
        obj.method.should.equal("POST");
        obj.qs.access_token.should.equal(fbConfig.pageToken);
        obj.json.recipient.id.should.equal("1234");
        obj.json.message.attachment.payload.elements[0].title.should.equal("title 1");
        done();
      };
      var facebookCont = proxyquire('../app/controllers/facebookController', {'request':request});
      
      facebookCont.sendCampaignMessage(jsonObj);

    });    
  });
});