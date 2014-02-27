var speakerService = function(){
    var speakers = [{id: 0, name: 'Kim Jong-Il'}, {id: 1, name: 'Vladimir Putin'}];

    return {
        findSpeakers: function() {
            return speakers;
        }
    };
};

var sessionService = function() {
    var sessions = [{name: 'Killing Bears'}, {name: 'Hiding nuclear weapons'}];

    return {
        findSessions: function() {
            return sessions;
        }
    };
};