/**
 * creates a twitter feed with the given data
 */
var createTwitterFeed = function() {
    var data = [{profileImage: 'https://pbs.twimg.com/profile_images/2441790961/b1nxj0dyy72d4gt17ylz_bigger.png',
                fullName: 'GasteinForum', nickName: 'GasteinForum', message: 'Our theme for #EHFG2014 is:\nELECTING HEALTH - THE EUROPE WE WANT!\nFind out more here: <a href="http://fb.me/18zI5XNVm">http://fb.me/18zI5XNVm</a>'}];

    var tweetContainer = $('#tweets');
    tweetContainer.children().remove();

    $.each(data, function(index, value) {
        var tweet = '<div class="tweet">';
        tweet += '<div class="names">';
        tweet += '<img class="profile-image" src=' + value.profileImage + '/>';
        tweet += '<span class="full-name">' + value.fullName + '</span>';
        tweet += '<span class="nickname">@' + value.nickName + '</span>';
        tweet += '</div>';

        tweet += '<p class="message">' + value.message + '</p>';
        tweet += '</div>';

        console.log(tweet);
        tweetContainer.append(tweet);
    });
}