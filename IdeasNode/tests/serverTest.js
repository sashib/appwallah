process.env.NODE_ENV = 'test';

var chai = require('chai'),
    chaiHttp = require('chai-http'),
    server = require('../server'),
    Idea = require('../models/idea'),
    testData = require('./testData');

var should = chai.should();

chai.use(chaiHttp);

describe('Server', function() {   
  describe('/webhook/', function() {
    it('should return 200 and call processMessage for POST');
  });
});
/*
describe('Server', function() {	  
  describe('api/ideas', function() {
    it('should return a 401 if no sender is passed to /ideas GET', function(done) {
  	  chai.request(server)
  	    .get('/api/ideas')
  	    .end(function(err, res) {
  	  	  res.should.have.status(401);
  	  	  done();
  	    });
    });
    it('should list 0 ideas for non-existent sender \'testuser100\' on /ideas?sender=testuser100 GET', function(done) {
  	  chai.request(server)
  	    .get('/api/ideas?sender=testuser100')
  	    .end(function(err, res) {
  	  	  res.should.have.status(200);
          res.body.should.be.a('array'); 
          res.body.should.have.lengthOf(0);
  	  	  done();
  	    });
    });
    it('should list limit of 5 ideas for sender \'testuser\' on /ideas?sender=testuser GET', function(done) {
  	  chai.request(server)
  	    .get('/api/ideas?sender=testuser')
  	    .end(function(err, res) {
  	  	  res.should.have.status(200);
          res.body.should.be.a('array'); 
          res.body.should.have.lengthOf(5);
  	  	  done();
  	    });
    });
    it('should search and list ideas with idea in it on /ideas?find=idea&sender=testuser GET', function(done) {
    	chai.request(server)
    	  .get('/api/ideas?find=idea&sender=testuser')
    	  .end(function(err, res) {
    	  	res.should.have.status(200);
    	  	//console.log(res.body);
    	  	res.body.should.be.a('array');
    	  	res.body[0].userId.should.equal('testuser');
			    res.body[0].userSource.should.equal('development');
			    res.body[0].description.should.contain('idea');  	  	
			    done();
    	  });
    });
    it('should return a 401 if no sender is passed to /ideas POST', function(done) {
    	chai.request(server)
    	  .post('/api/ideas')
    	  .end(function(err, res) {
    	  	res.should.have.status(401);
    	  	done();
    	  })
    })
    it('should add a SINGLE idea on /ideas POST', function(done) {
    	chai.request(server)
    	  .post('/api/ideas')
    	  .send({description:'testing new ideas #idea #sweet', sender:'testsender'})
    	  .end(function(err, res) {
    	  	res.should.have.status(200);
    	  	res.should.be.json;
    	  	res.body.should.be.a('object');
    	  	res.body.should.have.property('success');
    	  	res.body.success.should.be.a('object');
    	  	res.body.success.should.have.property('description');
    	  	res.body.success.should.have.property('userId');
    	  	res.body.success.should.have.property('userSource');
    	  	res.body.success.should.have.property('created');
    	  	res.body.success.should.have.property('hashtags');
    	  	res.body.success.description.should.equal('testing new ideas #idea #sweet');
    	  	res.body.success.userId.should.equal('testsender');
			    res.body.success.userSource.should.equal('development');
			    res.body.success.hashtags.should.eql(['#idea','#sweet']);    	  	
			    done();      
    	  })
    	  
    });

  });
});
*/