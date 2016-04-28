var express = require('express'),
    bodyParser = require('body-parser'),
    ideas = require('./routes/ideas'),
    path = require('path');

var app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


var router = express.Router();
router.route('/ideas')
      .post(ideas.addIdea)
      .get(ideas.findAll);

app.use('/api', router);

app.use(express.static('public'));
app.get('/', function (req, res) {
   res.sendFile(__dirname + '/public/index.html');
});



app.listen(3000);
console.log('Listening on port 3000...');