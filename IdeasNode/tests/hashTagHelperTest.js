process.env.NODE_ENV = 'test';

var expect = require('chai').expect;
var hashTagHelper = require('../app/helpers/hashTagHelper');

describe('hashTagHelper', function() {
  describe('getHashTags()', function () {
    it('should return [\'#idea\'] when the value is \'my new #idea\'', function () {
      var testStr = 'my new #idea';
      var actual = hashTagHelper.getHashTags(testStr);
      var expectedArr = ['#idea'];
      expect(actual).to.eql(expectedArr);
    });
    it('should return [\'#idea\', \'#idea2\'] when the value is \'my new #idea #idea2\'', function () {
      var testStr = 'my new #idea #idea2';
      var actual = hashTagHelper.getHashTags(testStr);
      var expectedArr = ['#idea', '#idea2'];
      expect(actual).to.eql(expectedArr);
    });
    it('should return [\'#idea\', \'#idea2\', \'#idea3\'] when the value is \'#idea my new #idea2 #idea3\'', function () {
      var testStr = '#idea my new #idea2 #idea3';
      var actual = hashTagHelper.getHashTags(testStr);
      var expectedArr = ['#idea', '#idea2', '#idea3'];
      expect(actual).to.eql(expectedArr);
    });
    it('should return [\'#ides\'] when the value is \'#idea \'', function () {
      var testStr = '#idea ';
      var actual = hashTagHelper.getHashTags(testStr);
      var expectedArr = ['#idea'];
      expect(actual).to.eql(expectedArr);
    });
    it('should return [] when the value is \'my new idea\'', function () {
      var testStr = 'my new idea';
      var actual = hashTagHelper.getHashTags(testStr);
      var expectedArr = [];
      expect(actual).to.eql(expectedArr);
    });
  });
});