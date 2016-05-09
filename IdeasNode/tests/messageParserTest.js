process.env.NODE_ENV = 'test';

var expect = require('chai').expect,
    messageHelper = require('../app/helpers/messageHelper'),
    messageController = require('../app/controllers/messageController'),
    Idea = require('../app/models/idea'),
    testData = require('./testData');

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
});