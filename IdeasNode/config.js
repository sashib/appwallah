var config = {};

config.mongoURI = {
  production: 'mongodb://db:ideasd3v@ds021691.mlab.com:21691/ideasdb',
  development: 'mongodb://localhost/ideas',
  test: 'mongodb://localhost/ideastest'
};

module.exports = config;