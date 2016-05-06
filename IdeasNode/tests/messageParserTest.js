process.env.NODE_ENV = 'test';

var expect = require('chai').expect,
    messageParser = require('../utils/messageParser'),
    messages = require('../utils/messages'),
    Idea = require('../models/idea'),
    testData = require('./testData');

describe('MessageParser', function() {
  describe('isNewIdea()', function () {
    it('should return true when the value is \'my new #idea\'', function () {
      var testStr = 'my new #idea';
      var actual = messageParser.isNewIdea(testStr);
      expect(actual).to.be.true;
    });
    it('should return false when the value is \'help\'', function () {
      var testStr = 'help';
      var actual = messageParser.isNewIdea(testStr);
      expect(actual).to.be.false;
    });

  });
  
  describe('isFindByHashTag()', function () {
    it('should return true when the value is \'find\'', function () {
      var testStr = 'find';
      var actual = messageParser.isFindByHashTag(testStr);
      expect(actual).to.be.true;
    });
    it('should return true when the value is \'find #something\'', function () {
      var testStr = 'find #something';
      var actual = messageParser.isFindByHashTag(testStr);
      expect(actual).to.be.true;
    });
    it('should return true when the value is \'#justonehashtag\'', function () {
      var testStr = '#justonehashtag';
      var actual = messageParser.isFindByHashTag(testStr);
      expect(actual).to.be.true;
    });
    it('should return false when the value is \'help some new idea\'', function () {
      var testStr = 'help some new idea';
      var actual = messageParser.isFindByHashTag(testStr);
      expect(actual).to.be.false;
    });
    it('should return false when the value is \'#one #two\'', function () {
      var testStr = '#one #two';
      var actual = messageParser.isFindByHashTag(testStr);
      expect(actual).to.be.false;
    });

  });
  
  describe('getFindHashTag()', function () {
    it('should return \'#idea\' when the value is \'find #idea\'', function () {
      var testStr = 'find #idea';
      var actual = messageParser.getFindHashTag(testStr);
      var expected = "#idea";
      expect(actual).to.eql(expected);
    });
    it('should return null when the value is \'find\'', function () {
      var testStr = 'find';
      var actual = messageParser.getFindHashTag(testStr);
      var expected = null;
      expect(actual).to.eql(expected);
    });
  });
  
  describe('handleFind()', function() {
    before(testData.createIdeaCollection);

    after(function(done){
      Idea.collection.drop();
      done();
    });
    it('should return a list of results matching \'awesome\' if text is \'find awesome\'', function(done) {
      var text = 'find awesome';
      var sender = 'testuser';
      var senderSource = 'development';
      var expected = 'a sixth #beautiful #grand new #awesome #idea\n\na third #grand new #awesome #idea\n\na fourth new #awesome #idea\n\n';
      messageParser.handleFind(sender, senderSource, text, function(sender, text) {
        console.log('handleFind callback, text is: ' + text);
        expect(text).to.equal(expected);
        done();
      })
    });
  });

  describe('handleNewIdea()', function() {
    it('should return ' + messages.ADDED_IDEA + ' if text is \'some new #idea test\'', function(done) {
      var text = 'some new #idea test';
      var sender = 'testsender';
      messageParser.handleNewIdea(sender, 'development', text, function(sender, text) {
        console.log('handleNewIdea callback, text is: ' + text);
        expect(text).to.equal(messages.ADDED_IDEA);
        done();
      })
    });
  });
});