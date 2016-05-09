process.env.NODE_ENV = 'test';

var expect = require('chai').expect,
    sinon = require('sinon'),
    req = require('Request'),
    response = require('http'),
    facebook = require('../app/controllers/facebookController');

describe('facebook', function() {

  describe('processMessage()', function () {    
    it('should parse facebook POST and call sendTextMessage');
  });

  describe('handleWebhookPost()', function() {
    it('should call processMessage and return status 200', function(done) {
      var res = { status:function(statusCode) {} };
      var sendObj = { send:function(msg) {} };   

      var resStatusSendStub = sinon.stub(sendObj, "send");
      var resStatusStub = sinon.stub(res, "status", function(statusCode) {
        return sendObj;
      });
      
      var stub = sinon.stub(facebook, "processMessage");

      facebook.handleWebhookPost(req, res);

      stub.called.should.be.ok;
      resStatusStub.calledWith(200).should.be.ok;
      resStatusSendStub.calledWith('Success').should.be.ok;
      
      done();      
    });
  });

  describe('sendTextMessage()', function() {
    it('should make a POST request with the sender and message');
  })
});