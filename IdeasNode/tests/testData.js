var Idea = require('../models/idea');

exports.createIdeaCollection = function(done) { 
  var ideaCollection = [
					     {
						   description: 'a first idea #idea',
						   userId: 'testuser',
						   userSource: 'development',
						   hashtags: ['#idea']
						 },
						 {
						   description: 'a second other #new #idea',
						   userId: 'testuser',
						   userSource: 'development',
						   hashtags: ['#idea', '#new']

						 },
						 {
						   description: 'a third #grand new #awesome #idea',
						   userId: 'testuser',
						   userSource: 'development',
						   hashtags: ['#idea', '#grand', '#awesome']

						 },
						 {
						   description: 'a fourth new #awesome #idea',
						   userId: 'testuser',
						   userSource: 'development',
						   hashtags: ['#idea', '#awesome']

						 },
						 {
						   description: 'a fifth #amazing idea',
						   userId: 'testuser',
						   userSource: 'development',
						   hashtags: ['#amazing']

						 },
						 {
						   description: 'a sixth #beautiful #grand new #awesome #idea',
						   userId: 'testuser',
						   userSource: 'development',
						   hashtags: ['#idea', '#grand', '#awesome', '#beautiful']

						 },
						 {
						   description: 'an idea for #newhome',
						   userId: 'testuser2',
						   userSource: 'development',
						   hashtags: ['#newhome']

						 }
					  ];

  Idea.create(ideaCollection, function(err, ideas) {
  	console.log('created a collection of items: ' + ideas.length);
  	done();
  });  
}