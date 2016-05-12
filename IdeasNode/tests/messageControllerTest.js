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
    
    it('should return a list of results matching \"awesome\" if text is \"find awesome\"', function(done) {
      messageController.handleFind(userId, userSource, text, 0, function(sender, text) {
        expect(text).to.contain(expected);
        done();
      });
    });
    it('should return a list of results and payload to the callback if results are 5 or more', function(done) {
      messageController.handleFind(userId, userSource, 'find', 0, function(sender, text, payload) {
        expect(text).to.contain(expected);
        expect(payload.sender).to.equal(userId);
        expect(payload.senderSource).to.equal(userSource);
        expect(payload.page).to.equal(1);
        expect(payload.searchText).to.equal(null);
        done();
      });

    });
    it('should return a list of results with ... if total result str is more then ' + messageController.MAX_CHARS_PER_TEXT, function(done) {
        var ellipsesExp = 'a fifth #amazing idea 12345678987654321123456789876543219999999999';
        messageController.handleFind(userId, userSource, 'find', 0, function(sender, text, payload) {
        expect(text).to.contain(ellipsesExp);
        done();
      });

    });

    it('should return a list of results and no payload to the callback if results are 5 or less', function(done) {
        messageController.handleFind(userId, userSource, 'find awesome', 0, function(sender, text, payload) {
        expect(text).to.contain(expected);
        expect(payload).to.equal(null);
        done();
      });

    });
    it('should return \"' + messageHelper.EMPTY_FIND_RESULTS + '\" if no results were found if text is \'find blah\'', function(done) {
      var testText = 'find blah';
      messageController.handleFind(userId, userSource, testText, 0, function(sender, txt) {
        expect(txt).to.equal(messageHelper.EMPTY_FIND_RESULTS );
        done();
      });      
    });
    it('should return \"' + messageHelper.NO_MORE_SEARCH_RESULTS + '\" if no more pages for results if postback has page out of scope', function(done) {
      messageController.handleFind(userId, userSource, text, 1, function(sender, txt) {
        expect(txt).to.equal(messageHelper.NO_MORE_SEARCH_RESULTS);
        done();
      });

    });
  });
  
  describe('handlePostback()', function() {
    var stub;
    before(function(done) {
      stub = sinon.stub(messageController, 'handleFind');
      done();
    });
    after(function(done) {
      stub.restore();
      done();
    });

    it('should call handleFind with payload and callback', function(done) {
      var payload = messageController.getPagingPayload('testuser', 'development', 'find', 1);
      var payloadStr = JSON.stringify(payload);
      var expected = 'a first idea #idea';
      var cb = function(sender, text, payload) {};
      messageController.handlePostback(payloadStr, cb);
      stub.calledWith(payload.sender, payload.senderSource, payload.searchText, payload.page, cb).should.be.ok;
      done();

    });
  })
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
    var stubHandleNewIdea;
    var stubHandleFind;
    var stubHandleHelp;

    before(function(done) {
      stubHandleNewIdea = sinon.stub(messageController, "handleNewIdea");
      stubHandleFind = sinon.stub(messageController, "handleFind");
      stubHandleHelp = sinon.stub(messageController, "handleHelp");
      done();
    });
    after(function(done) {
      stubHandleNewIdea.restore();
      stubHandleFind.restore();
      stubHandleHelp.restore();
      done();
    });
    it('should parse text and if it\'s a newidea then call handleNewIdea', function(done) {
      messageController.handleMessageReply('11111', 'developer', 'some new #idea', function(){});
      stubHandleNewIdea.called.should.be.ok;
      done();
    });
    it('should call handleNewIdea if test is \"find something good for #home\"', function(done) {
      messageController.handleMessageReply('11111', 'developer', 'find something good for #home', function(){});
      stubHandleNewIdea.called.should.be.ok;
      done();
    });
    it('should call handleNewIdea if test is \"help people in need #volunteer\"', function(done) {
      messageController.handleMessageReply('11111', 'developer', 'help people in need #volunteer', function(){});
      stubHandleNewIdea.called.should.be.ok;
      done();
    });
    it('should parse text and if it\'s a \"find something\" request then call handleFind', function(done) {
      messageController.handleMessageReply('11111', 'developer', 'find #idea', function(){});
      stubHandleFind.called.should.be.ok;
      done();
    });
    it('should parse text and if it\'s a \"find\" request then call handleFind', function(done) {
      messageController.handleMessageReply('11111', 'developer', 'find', function(){});
      stubHandleFind.called.should.be.ok;
      done();
    });
    it('should parse text and if it\'s a \"help\" request then call handleHelp', function(done) {
      messageController.handleMessageReply('11111', 'developer', 'help', function(){});
      stubHandleHelp.called.should.be.ok;
      done();

    });
    it('should parse text and if it\'s a \'tags\' request then call handleListTags');
  });
});