var PAGE_EVENT = 'pagebeforeshow';

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

        $('#speakerDetailHeader').text(speaker.fullName);
        $('#speakerDescription').text(speaker.description);
        $('#speakerImage').attr('src', speaker.imageUrl);
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

        $('#session-header').text(session.name);
    });
});

$('#map').on('pageshow', function() {
    var mapOptions = {
        center: new google.maps.LatLng(47.170329, 13.103852),
        zoom: 16
    };
    var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

    restCall("points/all", function(result) {
        for (i in result) {
           addMarker(map, result[i]);
        }
    });
});

$('#newsfeed').on(PAGE_EVENT, function() {
    loadAndCreateTwitterFeed();
    $('#refresh-icon').on('click', updateTwitterFeed);
});