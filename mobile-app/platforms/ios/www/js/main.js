var PAGE_EVENT = 'pagebeforeshow';

$('#sessions').on(PAGE_EVENT, function() {
    createSessionList('sessionList', sessionService().findSessions());
});


$('#speakers').on(PAGE_EVENT, function() {
    createListView('speakerList', speakerService().findSpeakers(), 'fullName', 'speaker-detail', 'id');
});

$('#speaker-detail').on(PAGE_EVENT, function() {
    var speaker = speakerService().findById($.mobile.pageParameters.id);
    if (speaker === null) {
        $.mobile.changePage('#speakers');
        return;
    }

    console.log(speaker);
    $('#speakerDetailHeader').text(speaker.fullName);
    $('#speakerDescription').text(speaker.description);
    $('#speakerImage').attr('src', speaker.imageUrl);
    createListView('speakerSessionList', sessionService().findBySpeakerId(speaker.id), 'name', 'session-detail', 'id');
});

$('#session-detail').on(PAGE_EVENT, function() {
    var session = sessionService().findById($.mobile.pageParameters.id);
    if (session === null) {
        $.mobile.changePage("#sessions");
        return;
    }

    $('#session-header').text(session.name);
});

$('#map').on('pageshow', function() {
    var mapOptions = {
        center: new google.maps.LatLng(47.170329, 13.103852),
        zoom: 16
    };
    var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

    restCall("points/all", function(result) {
        $.each(result, function(index, value) {
           addMarker(map, value);
        });
    });
});

$('#newsfeed').on(PAGE_EVENT, function() {
    loadAndCreateTwitterFeed();
    $('#refresh-icon').on('click', updateTwitterFeed);
});