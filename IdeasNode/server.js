var express = require('express'),
    bodyParser = require('body-parser'),
    ideas = require('./routes/ideas');

var app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var router = express.Router();
router.route('/ideas')
      .post(ideas.addIdea)
      .get(ideas.findAll);

app.use('/api', router);

app.set('port', process.env.PORT || 3000);
app.listen(app.get('port'));
console.log('Listening on port ' + app.get('port'));