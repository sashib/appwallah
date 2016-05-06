var express = require('express'),
    bodyParser = require('body-parser'),
    development = require('./routes/development'),
    facebook = require('./routes/facebook');

var app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// below will redirect http to https
app.use(function(req, res, next) {
  if(process.env.NODE_ENV == 'production') {
    if((!req.secure) && (req.get('X-Forwarded-Proto') !== 'https')) {
      var host = req.get('Host');
      var url = req.url;
      res.redirect('https://' + host + url);
    }
    else {
      next();
    }
  } else {
    next();  	
  }
});

console.log("NODE_ENV is: " + process.env.NODE_ENV);

if (process.env.NODE_ENV != 'production') {
  var router = express.Router();
  router.route('/ideas')
      .post(development.addIdea)
      .get(development.findAll);

  app.use('/api', router);
}
app.get('/webhook/', facebook.handleWebhookGet);
app.post('/webhook/', facebook.handleWebhookPost);

app.use(express.static('public'));
app.get('/', function (req, res) {
   res.sendFile(__dirname + '/public/index.html');
});

app.set('port', process.env.PORT || 3000);
app.listen(app.get('port'));

console.log('Node ready to rock. Listening on port ' + app.get('port'));

module.exports = app;

