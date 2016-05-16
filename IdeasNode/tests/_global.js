var testData = require('./testData'),
    Idea = require('../app/models/idea');



before(testData.createIdeaCollection);

after(function(done){
  Idea.collection.drop();
  done();
});
