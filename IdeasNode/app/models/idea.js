var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var IdeaSchema   = new Schema({
    userId: String,
    userSource: String,
    description: String,
    hashtags: [String],
    created: { type: Date, default: Date.now }

});

IdeaSchema.index({ description: 'text' });

module.exports = mongoose.model('Idea', IdeaSchema);