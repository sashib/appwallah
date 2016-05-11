process.env.NODE_ENV = 'test';

var should = require('chai').should(),
    sinon = require('sinon'),
    req = require('Request'),
    proxyquire = require('proxyquire'),
    messageHelper = require('../app/helpers/messageHelper'),
    facebook = require('../app/controllers/facebookController');

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
        obj.url.should.equal(facebook.FB_POST_URL);
        obj.method.should.equal("POST");
        obj.qs.access_token.should.equal(facebook.TOKEN);
        obj.json.recipient.id.should.equal(sender);
        obj.json.message.text.should.equal(text);
        done();
      };
      var facebookCont = proxyquire('../app/controllers/facebookController', {'request':request});
      
      facebookCont.sendTextMessage(sender, text);
    });
    it('should make a buttonTemplate POST request', function(done) {
      var sender = "11111";
      var payload = "sample payload";
      var text = "my ideas";
      var templateJson = facebook.buttonTemplatePagingJson(sender, text, payload);

      var request = function(obj, cb) {
        obj.url.should.equal(facebook.FB_POST_URL);
        obj.method.should.equal("POST");
        obj.qs.access_token.should.equal(facebook.TOKEN);
        obj.json.recipient.id.should.equal(sender);
        obj.json.message.attachment.payload.buttons[0].payload.should.equal(payload);
        done();
      };
      var facebookCont = proxyquire('../app/controllers/facebookController', {'request':request});
      
      facebookCont.sendTextMessage(sender, text, payload);

    });
  })
});