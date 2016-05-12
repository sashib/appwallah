var Idea = require('../app/models/idea');

module.exports.createIdeaCollection = function(done) { 
  var ideaCollection = [
					     {
						   description: 'a first idea #idea 12345678987654321123456789876543219999999999',
						   userId: 'testuser',
						   userSource: 'development',
						   hashtags: ['#idea']
						 },
						 {
						   description: 'a second other #new #idea 12345678987654321123456789876543219999999999',
						   userId: 'testuser',
						   userSource: 'development',
						   hashtags: ['#idea', '#new']

						 },
						 {
						   description: 'a third #grand new #awesome #idea 12345678987654321123456789876543219999999999',
						   userId: 'testuser',
						   userSource: 'development',
						   hashtags: ['#idea', '#grand', '#awesome']

						 },
						 {
						   description: 'a fourth new #awesome #idea 12345678987654321123456789876543219999999999',
						   userId: 'testuser',
						   userSource: 'development',
						   hashtags: ['#idea', '#awesome']

						 },
						 {
						   description: 'a fifth #amazing idea 12345678987654321123456789876543219999999999',
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