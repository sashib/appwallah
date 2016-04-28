var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var IdeaSchema   = new Schema({
    description: String,
    hashtags: [String],
    created: { type: Date, default: Date.now }

});

IdeaSchema.index({ hashtags: 'text' });

module.exports = mongoose.model('Idea', IdeaSchema);