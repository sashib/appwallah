var testData = require('./testData'),
    Idea = require('../models/idea');

before(testData.createIdeaCollection);

after(function(done){
  Idea.collection.drop();
  done();
});