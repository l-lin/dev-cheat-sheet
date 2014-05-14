/**
 * @author Oodrive
 * @author l.lin
 * @created 14/05/14 17:55
 */
(function () {
    'use strict';
    var data = {
        'google.fr': [
            {name: 'Chrome', value: '50%'},
            {name: 'FireFox', value: '30%'},
            {name: 'Internet Explorer', value: '20%' }
        ],
        'mozilla.fr': [
            {name: 'Chrome', value: '20%'},
            {name: 'FireFox', value: '60%'},
            {name: 'Internet Explorer', value: '20%'}
        ],
        'microsoft.fr': [
            {name: 'Chrome', value: '10%'},
            {name: 'FireFox', value: '20%'},
            {name: 'Internet Explorer', value: '70%'}
        ]
    };

    function groupByOS(site, memo, browser) {
        if (!memo[browser.name]) {
            memo[browser.name] = [];
        }
        memo[browser.name].push({site: site, value: browser.value});
        return memo;
    }

    var results = Object.keys(data).reduce(function (memo, site) {
        return data[site].reduce(groupByOS.bind(null, site), memo);
    }, {});

    console.log(results);
})();
