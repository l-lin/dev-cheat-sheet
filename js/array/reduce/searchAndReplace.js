/**
 * @author Oodrive
 * @author l.lin
 * @created 14/05/14 17:53
 */
(function () {
    'use strict';
    var input = "I'm %USER% and I live in %COUNTRY%";
    var data = [
        {token: '%USER%', value: 'lionel'},
        {token: '%COUNTRY%', value: 'France'}
    ];

    var output = data.reduce(function (memo, item) {
        return memo.replace(item.token, item.value);
    }, input);

    console.log(output);
})();

