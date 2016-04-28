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

app.listen(3000);
console.log('Listening on port 3000...');