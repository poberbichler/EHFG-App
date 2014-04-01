var PAGE_EVENT = 'pagebeforeshow';

var cacheAllData = function(webserviceUrl) {
    $.ajax(webserviceUrl, {
        headers: {'Access-Control-Allow-Origin': '*'},
        crossDomain: true,
        contentType: 'application/json',
        type: 'GET',
        dataType: 'jsonp'
    }).success(function(data) {
        speakerService().setData(data);
    });
};

cacheAllData("http://localhost:8080/rest/speaker/all");

$('#sessions').on(PAGE_EVENT, function() {
    createListView('sessionList', sessionService().findSessions(), 'name', 'session-detail', 'id');
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
    
    $('#speakerDetailHeader').text(speaker.fullName);
    $('#speakerDescription').text(speaker.description);
    createListView('speakerSessionList', sessionService().findBySpeakerId(speaker.id));
});

$('#session-detail').on(PAGE_EVENT, function() {
    var session = sessionService().findById($.mobile.pageParameters.id);
    if (session === null) {
        $.mobile.changePage("#sessions");
        return;
    }

    $('#session-header').text(session.name);
});

$('#map').on(PAGE_EVENT, function() {
    var mapOptions = {
        center: new google.maps.LatLng(47.170329, 13.103852),
        zoom: 16
    };
    var map = new google.maps.Map(document.getElementById("map-canvas"),
        mapOptions);
});

$('#newsfeed').on(PAGE_EVENT, function() {
    loadAndCreateTwitterFeed();
    $('#refresh-icon').on('click', updateTwitterFeed);
});