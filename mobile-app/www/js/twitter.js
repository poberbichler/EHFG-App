/**
 * adds the logic for representing the date for tweets to the normal date object
 * @returns {string} with the parsed input
 */
Date.prototype.toTwitterDate = function() {
    var seconds = 1000;
    var minutes = 1000 * 60;
    var hours = minutes * 60;
    var days = hours * 24;

    var difference = new Date().getTime() - this.getTime();
    if (difference < days) {
        if (difference < minutes) {
            var value = (difference/seconds).toFixed(0);
            return "" + value + (value == 1 ? " second ago" : " seconds ago");
        }

        if (difference < hours) {
            var value = (difference/minutes).toFixed(0);
            return "" + value + (value == 1 ? " minute ago" : " minutes ago");

        }

        var value = (difference/hours).toFixed(0);
        return "" + value + (value == 1 ? " hour ago" : " hours ago");
    }

    return "" + writtenShortMonth(this.getMonth())+ " " + this.getDay() + ", " + this.getHours() + ":" + this.getMinutes();
}

var writtenShortMonth = function(month) {
    switch (month) {
        case 0:
            return 'Jan';
        case 1:
            return 'Feb';
        case 2:
            return 'Mar';
        case 3:
            return 'Apr';
        case 4:
            return 'May';
        case 5:
            return 'Jun';
        case 6:
            return 'Jul';
        case 7:
            return 'Aug';
        case 8:
            return 'Sep';
        case 9:
            return 'Oct';
        case 10:
            return 'Nov';
        case 11:
            return 'Dec';
        default:
            return "";
    }
}


/**
 * creates a twitter feed with the given data
 */
var loadAndCreateTwitterFeed = function() {
	restCall("twitter/tweets", createTwitterFeed);
    restCall("twitter/hashtag", setHashtag);
}

/**
 *
 * @param result
 */
var updateTwitterFeed = function() {
    restCall("twitter/tweets", function(result) {
       console.log(result);
    });
}


var setHashtag = function(result) {
    $('#hashtag').text(result.hashtag);
}

var createTwitterFeed = function(tweets) {
    var tweetContainer = $('#tweets');
    tweetContainer.children().remove();

    $.each(tweets, function(index, value) {
        var date = new Date(value.timestamp);

        var tweet = '<div class="tweet">';
        tweet += '<div class="names">';
        tweet += '<img class="profile-image" src=' + value.profileImage + '/>';
        tweet += '<span class="full-name">' + value.fullName + '</span>';
        tweet += '<span class="nickname">@' + value.nickName + '</span>';
        tweet += '<span class="timestamp">' + date.toTwitterDate() + '</span>'
        tweet += '</div>';

        tweet += '<p class="message">' + value.message + '</p>';
        tweet += '</div>';

        tweetContainer.append(tweet);
    });
};