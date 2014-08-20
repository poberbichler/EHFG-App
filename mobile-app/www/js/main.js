var PAGE_EVENT = 'pagebeforeshow';
var FAVOURITE_SESSIONS = "favouriteSessions";

$('#session').on(PAGE_EVENT, function() {
    sessionService().findSessions(function(result) {
        createSessionList('sessionList', result);
    });
});


$('#speaker').on(PAGE_EVENT, function() {
    speakerService().findSpeakers(function(result) {
        createListView('speakerList', result, 'fullName', 'speaker-detail', 'id');
    })
});

$('#speaker-detail').on(PAGE_EVENT, function() {
    speakerService().findById($.mobile.pageParameters.id, function(speaker) {
        if (speaker === null) {
           $.mobile.changePage('#speaker');
           return;
        }

        $('#speakerDetailHeader').html(speaker.fullName);
        $('#speakerDescription').html(speaker.description);

        console.log('speakers has imageurl', speaker.imageUrl);
        var imageUrls = speaker.imageUrl.split("/");
        var imageUrl = 'img/speakers/' + imageUrls[imageUrls.length-1];

        console.log('query for', imageUrl, imageUrls)
        $.get(imageUrl).done(function() {
            $('#speakerImage').attr('src', imageUrl);
        }).error(function() {
            $('#speakerImage').attr('src', speaker.imageUrl);
        });

        sessionService().findBySpeakerId(speaker.id, function(sessions) {
            createListView('speakerSessionList', sessions, 'name', 'session-detail', 'id');
        });
    });
});

$('#session-detail').on(PAGE_EVENT, function() {
    sessionService().findById($.mobile.pageParameters.id, function(session) {
        if (session === null) {
            $.mobile.changePage("#session");
            return;
        }

        $('#sessionName').text(session.name);
        $('#session-header').text(session.code + ' - ' + session.name);
        $('#sessionDescription').html(session.description);
        $('#sessionLocation').text(session.location);
        $('#showOnMap').attr('href', '#map?location=' + session.location);

        if (getFavouriteSessions().indexOf(session.id) === -1) {
            $('#toggleFavourite').text('Add to Favourites');
        }

        else {
            $('#toggleFavourite').text('Remove from Favourites');
        }


        var start = new Date(session.start);
        var end = new Date(session.end);

        var timeText = start.toSessionTime() + ' - ' + end.toSessionTime();
        $('#sessionTime').text(timeText);

        speakerService().findByIds(session.speakers, function(speakers) {
           createListView('sessionSpeakerList', speakers, 'fullName', 'speaker-detail', 'id');
        });
    });
});

$('#map').on('pageshow', function() {
    var options = {
        center: new google.maps.LatLng(47.170329, 13.103852),
        zoom: 16
    };

    var map = new google.maps.Map(document.getElementById("map-canvas"), options);

    if ($.mobile.pageParameters !== undefined && $.mobile.pageParameters.location !== undefined) {
        locationService().findCoordinatesByName($.mobile.pageParameters.location, function(result) {
            var coord = result.coordinate;
            var mapCoordinate = new google.maps.LatLng(coord.xValue, coord.yValue);
            map.panTo(mapCoordinate);

            new google.maps.Marker({
                position: mapCoordinate,
                map: map
            });
        });
    }


    google.maps.event.addListenerOnce(map, 'idle', function() {
        var navHeight = $('.ui-navbar').height() + 15;

        var mapCanvas = $('.gm-style');
        mapCanvas.css('height', 'auto');
        mapCanvas.css('bottom', navHeight);

        var mapChild = mapCanvas.children(':first');
        mapChild.children(':first').css('height', 'auto');
        mapChild.children(':first').css('bottom', navHeight);
    });

    restCall("points/all", function(result) {
        for (var i in result) {
           addMarker(map, result[i]);
        }

    });
});

$('#newsfeed').on(PAGE_EVENT, function() {
    loadAndCreateTwitterFeed();
    $('#refresh-icon').on('click', updateTwitterFeed);
});


$('#toggleFavourite').on('click', function() {
    var sessionId = $.mobile.pageParameters.id;
    var favouriteSessions = getFavouriteSessions();

    var index = favouriteSessions.indexOf(sessionId);
    var linkText;
    if (index === -1) {
        favouriteSessions.push(sessionId);
        linkText = "Remove from Favourites";
    }

    else {
        favouriteSessions.splice(index, 1);
        linkText = "Add to Favourites";
    }

    $('#toggleFavourite').text(linkText);
    localStorage.setItem(FAVOURITE_SESSIONS, JSON.stringify(favouriteSessions));
});

var getFavouriteSessions = function() {
    var storage = JSON.parse(localStorage.getItem(FAVOURITE_SESSIONS));
    return storage || [];
}
