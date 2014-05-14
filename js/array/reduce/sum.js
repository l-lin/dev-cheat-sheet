/**
 * @author l.lin
 * @created 14/05/14 17:52
 */
(function () {
    'use strict';
    var sum = [1, 2, 3, 4, 5].reduce(function (memo, val) {
        return memo + val;
    });

    console.log(sum);
})();
