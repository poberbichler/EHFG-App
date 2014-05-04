var SPEAKER = "speaker";
var SESSION = "session";

/**
 * service layer for the various speaker queries
 */
var speakerService = function(){
    return {
        findSpeakers: function(callback) {
            checkForItem(SPEAKER, callback);
        },

        findById: function(speakerId, callback) {
            checkForItemById(SPEAKER, speakerId, callback);
        }
    };
};

/**
 * service layer for the various session queries
 */
var sessionService = function() {
    return {
        findSessions: function(callback) {
            checkForItem(SESSION, callback);
        },

        findById: function(sessionId, callback) {
            checkForItem(SESSION, function(days) {
                for (var i in days) {
                    var sessions = days[i].sessions;
                    for (var j in sessions) {
                        var session = sessions[j];
                        if (session.id == sessionId) {
                            callback(session);
                            return;
                        }
                    }
                }
            });
        },

        findBySpeakerId: function(speakerId, callback) {
            checkForItem(SESSION, function(days) {
                var result = [];
                for (var i in days) {
                    var sessions = days[i].sessions;
                    for (var j in sessions) {
                        var session = sessions[j];
                        if (session.speakers.indexOf(speakerId) !== -1) {
                            result.push(session);
                        }
                    }
                }

                callback(result);
            })
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
var checkForItem = function(itemName, callback) {
    var data = localStorage.getItem(itemName);
    if (data === null) {
        restCall(itemName + '/all', function(result) {
            localStorage.setItem(itemName, JSON.stringify(result));
            callback(result);
        });
    }

    else {
        callback(JSON.parse(data));
    }
}


checkForItemByProperty = function(itemName, callback, idValue, property) {
    checkForItem(itemName, function(data) {
        for (var i in data) {
            if ($.isArray(property)) {
            }

            else if (data[i][property] == idValue) {
                callback(data[i]);
                return;
            }
        }
    });
}

checkForItemById = function(itemName, value, callback) {
    checkForItemByProperty(itemName, callback, value, 'id');
}