$('#sessions').on('pagebeforeshow', function() {
    createListView('sessionList', sessionService().findSessions());
});


$('#speakers').on('pagebeforeshow', function() {
    createListView('speakerList', speakerService().findSpeakers(), 'name', 'speaker-detail', 'id');
});

$('#speaker-detail').on('pagebeforeshow', function(a, b, c) {
   console.log(a,b,c);
});