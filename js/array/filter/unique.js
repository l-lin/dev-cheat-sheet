/**
 * @author l.lin
 * @created 26/11/14 16:59
 */
(function() {
    'use strict';

    function onlyUnique(value, index, self) {
        return self.indexOf(value) === index;
    }

    var data = ['a', 1, 'a', 2, '1'];
    var unique = data.filter( onlyUnique );
    console.log(unique); // ['a', 1, 2, '1']
})();
