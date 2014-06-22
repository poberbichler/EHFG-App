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
        //$('#speakerDescription').text(speaker.description);
        $('#speakerDescription').html(speaker.description);
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
        for (i in result) {
           addMarker(map, result[i]);
        }

    });
});

$('#newsfeed').on(PAGE_EVENT, function() {
    loadAndCreateTwitterFeed();
    $('#refresh-icon').on('click', updateTwitterFeed);
});