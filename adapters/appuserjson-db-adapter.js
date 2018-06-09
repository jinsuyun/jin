var mysql = require('mysql');
var dbConfig = {
    host: '18.221.204.247',
    port: '3306',
    user: 'root', // mysql user
    password: '5907', // mysql password
    database: 'capstone',
    connectionLimit: 10
}

var dbResult = require('../routes/result');
var pool = mysql.createPool(dbConfig);
var adapter = {};

var userSearchQuery = 'SELECT * FROM appuser';

adapter.userSearch = function(cb) {
    var resultCode = dbResult.Fail;

    pool.getConnection(function(err, connection) {
        if (err) { // db연결실패
            console.log(err);
            resultCode = dbResult.Fail;
            connection.release();
            cb(resultCode, []);
        } else { // db연결성공
            connection.query(userSearchQuery, function(err, rows) {
                if (err) { // 로그인 실패
                    console.log(err);
                    resultCode = dbResult.Fail;
                    connection.release();
                    cb(resultCode, []);
                } else { // 로그인 성공
                    resultCode = dbResult.OK;
                    connection.release();
                    cb(resultCode, rows);
                }
            });
        }
    });
}

module.exports = adapter;