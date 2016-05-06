process.env.NODE_ENV = 'test';

var should = require('chai').should(),
    ideas = require('../routes/ideas'),
    Idea = require('../models/idea'),
    testData = require('./testData');


describe('ideas', function() {
  describe('find()', function() {
    it('should return a list of results matching \'awesome\'', function(done) {
      var text = 'awesome';
      var sender = 'testuser';
      var senderSource = 'development';
      var cb = function(err, ideas) {
        ideas.should.have.lengthOf(3);
        done();
      };
      ideas.find(sender, senderSource, text, cb);
    });
  });

  describe('addIdea()', function() {
    it('should return Idea object if text is \'some new #idea #test\'', function(done) {
      var text = 'some new #idea #test';
      var sender = 'testuser';
      ideas.addIdea(sender, 'development', text, function(err, idea) {
        idea.should.be.a('object');
        idea.should.have.property('description');
        idea.should.have.property('userId');
        idea.should.have.property('userSource');
        idea.should.have.property('created');
        idea.should.have.property('hashtags');
        idea.description.should.equal('some new #idea #test');
        idea.userId.should.equal('testuser');
        idea.userSource.should.equal('development');
        idea.hashtags.should.eql(['#idea', '#test']);         
        done();
      })
    });
  });
});