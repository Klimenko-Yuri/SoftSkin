const express = require('express');

const obj = {
    first: true,
    second: false,
    third: true
}

const app = express();
app.use(express.static(__dirname + '/client'))
app.get('/input', function(request, response) {
    let ansver = undefined;
    for (key in obj) {
        if (request.query.data == key) {
            ansver = obj[key];
        }
    }
    response.send(ansver)
})
app.listen(3000);