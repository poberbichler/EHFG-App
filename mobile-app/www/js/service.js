/**
 * service layer for the various speaker queries
 */
var speakerService = function(){
    return {
        findSpeakers: function() {
            return JSON.parse(localStorage.getItem('speakers'));
        },

        findById: function(speakerId) {
            return findByIdInList(JSON.parse(localStorage.getItem('speakers')), speakerId);
        },

        setData: function(data) {
            localStorage.setItem('speakers', JSON.stringify(data));
        }
    };
};

/**
 * service layer for the various session queries
 */
var sessionService = function() {
    var sessions = [{id: 0, name: 'Killing Bears', speakerId: 1, location: 0},
                    {id: 1, name: 'Hiding nuclear weapons', speakerId: 0, location: 0},
                    {id: 2, name: 'blup die tote gans', speakerId: 0, location: 1}
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

/**
 * service layer for the various location queries
 */
var locationService = function() {
    var locations = [{id: 0, name: 'Congress Center 1'},
        {id: 1, name: 'Congress Center 2'},
        {id: 2, name: 'Kursaal A'},
        {id: 3, name: 'Kursaal B'}
    ];

    return {

    };
};