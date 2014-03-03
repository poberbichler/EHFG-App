var PAGE_EVENT = 'pagebeforeshow';

var cacheAllData = function(webserviceUrl) {
    $.ajax(webserviceUrl, {
        headers: {'Access-Control-Allow-Origin': '*'},
        crossDomain: true,
        contentType: 'application/json',
        type: 'GET',
        dataType: 'jsonp'
    }).success(function(data) {
        console.log('success', data);
    });
};

cacheAllData("http://localhost:8080/rest/speaker/all");

$('#sessions').on(PAGE_EVENT, function() {
    createListView('sessionList', sessionService().findSessions(), 'name', 'session-detail', 'id');
});


$('#speakers').on(PAGE_EVENT, function() {
    createListView('speakerList', speakerService().findSpeakers(), 'name', 'speaker-detail', 'id');
});

$('#speaker-detail').on(PAGE_EVENT, function() {
    var speaker = speakerService().findById(0);
    if (speaker === null) {
        $.mobile.changePage('#speakers');
        return;
    }
    
    $('#speakerDetailHeader').text(speaker.name);
    $('#speakerDescription').text(speaker.description);
    createListView('speakerSessionList', sessionService().findBySpeakerId(speaker.id));
});

$('#session-detail').on(PAGE_EVENT), function() {
    var session = sessionService().findById(0);
    if (session === null) {
        $.mobile.changePage("#sessions");
        return;
    }

    $('#session-header').text(session.name);
};