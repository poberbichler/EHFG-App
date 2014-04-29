var SPEAKER = "speaker";
var SESSION = "session";

/**
 * service layer for the various speaker queries
 */
var speakerService = function(){
    return {
        findSpeakers: function() {
            return JSON.parse(localStorage.getItem(SPEAKER));
        },

        findById: function(speakerId) {
            return findByIdInList(JSON.parse(localStorage.getItem(SPEAKER)), speakerId);
        }
    };
};

/**
 * service layer for the various session queries
 */
var sessionService = function() {
    return {
        findSessions: function() {
            return JSON.parse(localStorage.getItem(SESSION));
        },

        findById: function(sessionId) {
            var days = JSON.parse(localStorage.getItem(SESSION));
            var result = null;
            $.each(days, function(dayIndex, currentDay) {
                $.each(currentDay.sessions, function(sessionIndex, currentSession) {
                    if (currentSession.id == sessionId) {
                        result = currentSession;
                        return;
                    }
                });
            });

            return result;
        },

        findBySpeakerId: function(speakerId) {
            var days = JSON.parse(localStorage.getItem(SESSION));
            var result = [];
            $.each(days, function(dayIndex, currentDay) {
                $.each(currentDay.sessions, function(sessionIndex, currentSession) {
                    if (currentSession.speakers.indexOf(speakerId) !== -1) {
                        result.push(currentSession);
                    }
                });
            });

            return result;
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
/**
 * checks for a specific item in the local storage, and returns an object containing the data
 * if nothing is found in the local storage, data will be fetched from the backend
 *
 * @param itemName
 */
var checkForItem = function(itemName) {
    var data = localStorage.getItem(itemName);
    if (data === null) {
        restCall(itemName + '/all', function(result) {
            localStorage.setItem(itemName, JSON.stringify(result));
        });
    }
}


checkForItem(SPEAKER);
checkForItem(SESSION);