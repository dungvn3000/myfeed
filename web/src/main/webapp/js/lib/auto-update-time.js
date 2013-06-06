/**
 * User: phphuc
 * Date: 2/22/13
 * Time: 5:52 PM
 */

setInterval(function () {
    $('.sinceTime').each(function () {
        var originalTime = parseInt($(this).attr('originalTime'));
        $(this).text(getTimeSince(originalTime));
    });
}, 60000);

/**
 * date_suffix()
 * returns the date suffix (st,nd,rd,th) for a given day in a month
 */

function date_suffix(date) {
    if (date == 1 || date == 21 || date == 31) {
        return 'st';
    } else if (date == 2 || date == 22) {
        return 'nd';
    } else if (date == 3 || date == 23) {
        return 'rd';
    } else {
        return 'th';
    }
}

/**
 * time_since()
 * returns the time passed since a given unix_timestamp.
 * eg. 10 seconds ago, 1 hour ago, 10th Sep etc
 */
function getTimeSince(timeAsMillis) {
    var date = new Date(timeAsMillis);

    var str = '';

    var months = [
        'Jan',
        'Feb',
        'Mar',
        'Apr',
        'May',
        'Jun',
        'Jul',
        'Aug',
        'Sep',
        'Oct',
        'Nov',
        'Dec'
    ];

    var chunks = [
        [31536000000, 'year'],
        [2592000000, 'month'],
        [604800000, 'week'],
        [86400000, 'day'],
        [3600000, 'hour'],
        [60000, 'minute'],
        [1000, 'second']
    ];

    var today = new Date();
    var since = new Date(today.getTime() - date.getTime());

    if (since.getTime() < 60000) {
        return "a few seconds ago";
    }


    if (since.getTime() > 604800000) {
        str = months[date.getMonth()] + ' ' + date.getDate() + date_suffix(date.getDate());

        if (since.getTime() > 31536000000) {
            str = str + ', ' + date.getFullYear();
        }

        return str;
    }

    var ms = 0;
    var name = 0;
    var i = 0;
    var ic = chunks.length;
    var count = 0;

    for (i = 0; i < ic; i++) {
        ms = chunks[i][0];
        name = chunks[i][1];

        count = Math.floor(since.getTime() / ms);
        if (count != 0) {
            break;
        }
    }

    return ((count == 1) ? 'a' : count) + ' ' + name + ((count == 1) ? '' : 's') + ' ago';
}

