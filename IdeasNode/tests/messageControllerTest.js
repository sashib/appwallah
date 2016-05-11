process.env.NODE_ENV = 'test';

var chai = require('chai'),
    sinon = require('sinon'),
    messageHelper = require('../app/helpers/messageHelper'),
    messageController = require('../app/controllers/messageController'),
    Idea = require('../app/models/idea'),
    testData = require('./testData');

var expect = chai.expect;
var should = chai.should();

describe('messageController', function() {  
  describe('handleFind()', function() {

    it('should return a list of results matching \'awesome\' if text is \'find awesome\'', function(done) {
      var text = 'find awesome';
      var userId = 'testuser';
      var userSource = 'development';
      var expected = 'a sixth #beautiful #grand new #awesome #idea';//\n\na third #grand new #awesome #idea\n\na fourth new #awesome #idea\n\n';
      messageController.handleFind(userId, userSource, text, function(sender, text) {
        expect(text).to.contain(expected);
        done();
      })
    });
  });

  describe('handleNewIdea()', function() {
    it('should return ' + messageHelper.ADDED_IDEA + ' if text is \'some new #idea test\'', function(done) {
      var text = 'some new #idea test';
      var sender = 'testsender';
      messageController.handleNewIdea(sender, 'development', text, function(sender, text) {
        expect(text).to.equal(messageHelper.ADDED_IDEA);
        done();
      })
    });
  });
  describe('handleMessageReply(sender, senderSource, text, cb)', function() {
    it('should parse text and if it\'s a newidea then call handleNewIdea', function(done) {
      var stubHandleNewIdea = sinon.stub(messageController, "handleNewIdea");
      messageController.handleMessageReply('11111', 'developer', 'some new #idea', function(){});
      stubHandleNewIdea.called.should.be.ok;
      done();
    });
    it('should parse text and if it\'s a \'find something\' request then call handleFind', function(done) {
      var stubHandleFind = sinon.stub(messageController, "handleFind");
      messageController.handleMessageReply('11111', 'developer', 'find #idea', function(){});
      stubHandleFind.called.should.be.ok;
      stubHandleFind.restore();
      done();
    });
    it('should parse text and if it\'s a \'find\' request then call handleFind', function(done) {
      var stubHandleFind = sinon.stub(messageController, "handleFind");
      messageController.handleMessageReply('11111', 'developer', 'find', function(){});
      stubHandleFind.called.should.be.ok;
      stubHandleFind.restore();
      done();
    });
    it('should parse text and if it\'s a \'help\' request then call handleHelp');
    it('should parse text and if it\'s a \'tags\' request then call handleListTags');
  })
});