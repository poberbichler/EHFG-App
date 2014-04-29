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
    return {
        findSessions: function() {
            return JSON.parse(localStorage.getItem('sessions'));
        },

        findById: function(sessionId) {
            var days = JSON.parse(localStorage.getItem('sessions'));
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
            var days = JSON.parse(localStorage.getItem('sessions'));
            var result = [];
            $.each(days, function(dayIndex, currentDay) {
                $.each(currentDay.sessions, function(sessionIndex, currentSession) {
                    if (currentSession.speakers.indexOf(speakerId) !== -1) {
                        result.push(currentSession);
                    }
                });
            });

            return result;
        },

        setData: function(data) {
            localStorage.setItem('sessions', JSON.stringify(data));
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
            localStorage.setItem('itemName', result);
        });
    }

    return JSON.parse(data);
}

checkForItem('asdfasdf');