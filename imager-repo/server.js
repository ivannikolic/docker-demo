var express = require("express"),
    app = express(),
    formidable = require('formidable'),
    util = require('util'),
    fs = require('fs-extra'),
    qt = require('quickthumb');

app.use(qt.static(__dirname + '/'));

console.log('Starting NodeJS');


var uploadsDir = "image/";

app.post('/upload', function (req, res) {
    try {
        var form = new formidable.IncomingForm();
        form.parse(req, function (err, fields, files) {
            res.writeHead(200);
            res.end();
        });

        form.on('end', function (fields, files) {
            var temp_path = this.openedFiles[0].path;
            var file_name = this.openedFiles[0].name;

            fs.copy(temp_path, uploadsDir + file_name, function (err) {
                if (err) {
                    console.error(err);
                } else {
                    console.log("uploaded: " +  uploadsDir + file_name)
                }
            });
        });
    } catch (ex) {
        console.error(ex);
    }
});
app.listen(80);