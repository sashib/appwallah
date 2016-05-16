process.env.NODE_ENV = 'test';

var should = require('chai').should(),
    ideaController = require('../app/controllers/ideaController'),
    testData = require('./testData');


describe('ideaController', function() {
  describe('find()', function() {
    it('should return a list of results matching \'awesome\'', function(done) {
      var text = 'awesome';
      var sender = 'testuser';
      var senderSource = 'development';
      var cb = function(err, ideas) {
        ideas.should.have.lengthOf(3);
        done();
      };
      ideaController.find(sender, senderSource, text, 0, cb);
    });
    it('should return a list of results matching \'first idea\'', function(done) {
      var text = 'first';
      var sender = 'testuser';
      var senderSource = 'development';
      var cb = function(err, ideas) {
        ideas.should.have.lengthOf(1);
        ideas[0].description.should.equal('a first idea #idea 12345678987654321123456789876543219999999999');
        done();
      };
      ideaController.find(sender, senderSource, text, 0, cb);
    });
    it('should return first page of 5 results', function(done) {
      var text = '';
      var sender = 'testuser';
      var senderSource = 'development';
      var cb = function(err, ideas) {
        ideas.should.have.lengthOf(ideaController.IDEA_QUERY_LIMIT);
        done();
      };
      ideaController.find(sender, senderSource, text, 0, cb);
    });
    it('should return second page of 3 results', function(done) {
      var text = '';
      var sender = 'testuser';
      var senderSource = 'development';
      var cb = function(err, ideas) {
        ideas.should.have.lengthOf(ideaController.IDEA_QUERY_LIMIT);
        done();
      };
      ideaController.find(sender, senderSource, text, 1, cb);
    });
  });

  describe('addIdea()', function() {
    it('should return Idea object if text is \'some new #idea #test\'', function(done) {
      var text = 'some new #idea #test';
      var sender = 'testuser';
      ideaController.addIdea(sender, 'development', text, function(err, idea) {
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