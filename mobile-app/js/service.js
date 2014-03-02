var speakerService = function(){
    var speakers = [{id: 0, name: 'Kim Jong-Il', description: 'LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM '},
        {id: 1, name: 'Vladimir Putin'}];

    return {
        findSpeakers: function() {
            return speakers;
        },

        findById: function(speakerId) {
            return findByIdInList(speakers, speakerId);
        }
    };
};

var sessionService = function() {
    var sessions = [{id: 0, name: 'Killing Bears', speakerId: 1},
                    {id: 1, name: 'Hiding nuclear weapons', speakerId: 0},
                    {id: 2, name: 'blup die tote gans', speakerId: 0}
    ];

    return {
        findSessions: function() {
            return sessions;
        },

        findById: function(sessionId) {
            return findByIdInList(sessions, sessionId);
        },

        findBySpeakerId: function(speakerId) {
            return findListByPropertyNameInList(sessions, 'speakerId', speakerId);
        }
    };
};