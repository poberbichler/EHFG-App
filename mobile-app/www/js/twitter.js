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
            return "" + value + "s"
        }

        if (difference < hours) {
            var value = (difference/minutes).toFixed(0);
            return "" + value + "m"

        }

        var value = (difference/hours).toFixed(0);
        return "" + value + "h";
    }

    var minuteValue = this.getMinutes() < 10 ? "0" + this.getMinutes() : this.getMinutes();
    return "" + writtenShortMonth(this.getMonth())+ " " + this.getDay() + ", " + this.getHours() + ":" + minuteValue;
}

/**
 * short representation of a single month
 *
 * @param month to be represented
 */
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
	restCall("twitter/tweetpage/0", createTwitterFeed);
    restCall("twitter/hashtag", setHashtag);
}

/**
 * updates the current twitter feed
 */
var updateTwitterFeed = function() {
    var latestTweet = JSON.parse(localStorage.getItem('lastTweet'));
    if (latestTweet !== null) {
        restCallWithParams("twitter/update", {lastTweet: new Date(latestTweet.timestamp)}, function(result) {
            $(createTwitterElements(result)).prependTo($('#tweets')).hide().slideDown(500);
            updateTimestamps();
        });
    }

    else {
        loadAndCreateTwitterFeed();
    }
}

/**
 *
 * @param result
 */
var setHashtag = function(result) {
    $('#hashtag').text(result.hashtag);
}

var createTwitterFeed = function(tweetPage) {
    var tweetContainer = $('#tweets');
    tweetContainer.children().remove();
    tweetContainer.append(createTwitterElements(tweetPage));
};

/**
 * takes an array of tweets, and created a dom element from it
 *
 * @param tweets to be parsed
 * @returns {string} html text representing these tweets
 */
var createTwitterElements = function(tweetPage) {
    var tweets = [];
    if ($.isArray(tweetPage)) {
        tweets = tweetPage;
    }

    else {
        tweets = tweetPage.tweets;
    }

    var tweet = '';
    $.each(tweets, function(index, value) {
        if (index === 0) {
            var currentLastItem = JSON.parse(localStorage.getItem('lastTweet'));
            if (currentLastItem === undefined || currentLastItem === null || value.timestamp > currentLastItem.timestamp) {
                localStorage.setItem('lastTweet', JSON.stringify(value));
            }
        }

        var date = new Date(value.timestamp);

        tweet += '<div class="tweet">';
        tweet += '<div class="names">';
        tweet += '<img class="profile-image" src=' + value.profileImage + '/>';
        //tweet += '<span class="full-name">' + (clientIsIphone() ? value.fullNameForScreen : value.fullName) + '</span>';
        tweet += '<div class="name-wrapper">';
        tweet += '<span class="full-name">' + value.fullName + '</span>';
        tweet += '<span class="nickname">@' + value.nickName + '</span>';
        tweet += '</div>';
        tweet += '<span class="timestamp" data-timestamp1="' + date.getTime() + '">' + date.toTwitterDate() + '</span>'
        tweet += '</div>';

        tweet += '<p class="message">' + value.message + '</p>';
        tweet += '</div>';
    });

    if (tweetPage.morePages !== undefined && tweetPage.morePages) {
        var moreTweetElement = '<div id="more-tweets" onclick="loadMoreTweets(';
        moreTweetElement += (Number(tweetPage.currentPage) + 1) + ')">Load More</div>';

        tweet += moreTweetElement;
    }

    return tweet;
}

/**
 * sends the rest call to the backend, and updates the specific elements
 * @param nextPage
 */
var loadMoreTweets = function(nextPage) {
    restCall("twitter/tweetpage/" + nextPage, function(result) {
        $('#more-tweets').remove();
        $(createTwitterElements(result)).appendTo($('#tweets')).hide().slideDown(250);
    });
}

/**
 * checks for every element with the timestamp class, and update it
 */
var updateTimestamps = function() {
    $('.timestamp').each(function(index, element) {
        var timestamp = $(element).data('timestamp');
        if (timestamp !== undefined) {
            $(element).text(new Date(timestamp).toTwitterDate());
        }
    });
}
