process.env.NODE_ENV = 'test';

var expect = require('chai').expect,
    messageHelper = require('../app/helpers/messageHelper'),
    Idea = require('../app/models/idea'),
    testData = require('./testData');

describe('messageHelper', function() {
  describe('isNewIdea()', function () {
    it('should return true when the value is \'my new #idea\'', function () {
      var testStr = 'my new #idea';
      var actual = messageHelper.isNewIdea(testStr);
      expect(actual).to.be.true;
    });
    it('should return false when the value is \'help\'', function () {
      var testStr = 'help';
      var actual = messageHelper.isNewIdea(testStr);
      expect(actual).to.be.false;
    });

  });
  
  describe('isFindByHashTag()', function () {
    it('should return true when the value is \'find\'', function () {
      var testStr = 'find';
      var actual = messageHelper.isFindByHashTag(testStr);
      expect(actual).to.be.true;
    });
    it('should return true when the value is \'find #something\'', function () {
      var testStr = 'find #something';
      var actual = messageHelper.isFindByHashTag(testStr);
      expect(actual).to.be.true;
    });
    it('should return true when the value is \'#justonehashtag\'', function () {
      var testStr = '#justonehashtag';
      var actual = messageHelper.isFindByHashTag(testStr);
      expect(actual).to.be.true;
    });
    it('should return false when the value is \'help some new idea\'', function () {
      var testStr = 'help some new idea';
      var actual = messageHelper.isFindByHashTag(testStr);
      expect(actual).to.be.false;
    });
    it('should return false when the value is \'#one #two\'', function () {
      var testStr = '#one #two';
      var actual = messageHelper.isFindByHashTag(testStr);
      expect(actual).to.be.false;
    });

  });
  
  describe('getFindHashTag()', function () {
    it('should return \'#idea\' when the value is \'find #idea\'', function () {
      var testStr = 'find #idea';
      var actual = messageHelper.getFindHashTag(testStr);
      var expected = "#idea";
      expect(actual).to.eql(expected);
    });
    it('should return null when the value is \'find\'', function () {
      var testStr = 'find';
      var actual = messageHelper.getFindHashTag(testStr);
      var expected = null;
      expect(actual).to.eql(expected);
    });
  });
  

});