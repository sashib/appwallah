process.env.NODE_ENV = 'test';

var expect = require('chai').expect;
var sinon = require('sinon');
var facebook = require('../routes/facebook');
var messages = require('../utils/messages');

describe('facebook', function() {
  describe('processMessage()', function () {    
    it('should parse facebook POST and call sendTextMessage');
  });

  describe('handleWebhookPost()', function() {
    it('should call processMessage and return status 200');
  });

  describe('sendTextMessage()', function() {
    it('should make a POST request with the sender and message');
  })
});