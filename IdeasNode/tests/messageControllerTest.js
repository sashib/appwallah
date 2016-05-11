process.env.NODE_ENV = 'test';

var chai = require('chai'),
    sinon = require('sinon'),
    proxyquire = require('proxyquire'),
    messageHelper = require('../app/helpers/messageHelper'),
    messageController = require('../app/controllers/messageController'),
    Idea = require('../app/models/idea'),
    testData = require('./testData');

var expect = chai.expect;
var should = chai.should();

describe('messageController', function() {  
  describe('handleHelp()', function() {
    it('should return help info if text is \'help\'', function(done) {
      var helpText = messageHelper.HELP;
      var userId = 'testuser';
      messageController.handleHelp(userId, function(sender, text) {
        expect(text).to.equal(helpText);
        expect(sender).to.equal(userId);
        done();
      })
    });
  });

  describe('handleFind()', function() {
    var text = 'find awesome';
    var userId = 'testuser';
    var userSource = 'development';
    var expected = 'a sixth #beautiful #grand new #awesome #idea';//\n\na third #grand new #awesome #idea\n\na fourth new #awesome #idea\n\n';
    
    it('should return a list of results matching \'awesome\' if text is \'find awesome\'', function(done) {
      messageController.handleFind(userId, userSource, 0, text, function(sender, text) {
        expect(text).to.contain(expected);
        done();
      });
    });
    it('should return \"' + messageHelper.EMPTY_FIND_RESULTS + '\" if no results were found', function(done) {
      var testText = 'find sdafdswer';
      messageController.handleFind(userId, userSource, testText, 0, function(sender, txt) {
        expect(txt).to.equal(messageHelper.EMPTY_FIND_RESULTS );
        done();
      });      
    });
    it('should return \"' + messageHelper.NO_MORE_SEARCH_RESULTS + '\" if no more pages for results', function(done) {
      messageController.handleFind(userId, userSource, text, 1, function(sender, txt) {
        expect(txt).to.equal(messageHelper.NO_MORE_SEARCH_RESULTS);
        done();
      });

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
    it('should parse text and if it\'s a \'help\' request then call handleHelp', function(done) {
      var stubHandleHelp = sinon.stub(messageController, "handleHelp");
      messageController.handleMessageReply('11111', 'developer', 'help', function(){});
      stubHandleHelp.called.should.be.ok;
      stubHandleHelp.restore();
      done();

    });
    it('should parse text and if it\'s a \'tags\' request then call handleListTags');
  });
  describe('handlePostback(payload)', function() {
    it('should return list of results for the page in payload');
  })
});