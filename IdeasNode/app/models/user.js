var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var UserSchema   = new Schema({
    userId: String,
    userSource: String,
    email: String,
    phone: String,
    name: String,
    created: { type: Date, default: Date.now }

});

module.exports = mongoose.model('User', UserSchema);