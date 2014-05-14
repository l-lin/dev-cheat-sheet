/**
 * @author l.lin
 * @created 14/05/14 17:53
 */
(function () {
    'use strict';
    var stats = [
        {'site': 'google.fr', 'browser': 'Chrome', 'value': '50%'},
        {'site': 'google.fr', 'browser': 'FireFox', 'value': '30%'},
        {'site': 'google.fr', 'browser': 'Internet Explorer', 'value': '20%'},
        {'site': 'mozilla.fr', 'browser': 'FireFox', 'value': '60%'},
        {'site': 'mozilla.fr', 'browser': 'Internet Explorer', 'value': '20%'},
        {'site': 'microsoft.fr', 'browser': 'Chrome', 'value': '10%'},
        {'site': 'microsoft.fr', 'browser': 'FireFox', 'value': '20%'}
    ];

    function compareSite(site, item) {
        return site === item.site;
    }

    function containSite(site, items) {
        return items.some(compareSite.bind(null, site));
    }

    function groupBySite(memo, item) {
        var site = memo.filter(containSite.bind(null, item.site));
        if (site.length > 0) {
            site[0].push(item);
        } else {
            memo.push([item]);
        }
        return memo;
    }

    // We use an empty table as the accumulator
    var results = stats.reduce(groupBySite, []);

    console.log(results);
})();
