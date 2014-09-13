var SPEAKER = "speaker";
var SESSION = "session";
var LAST_UPDATE = "lastUpdate";

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
        },

        findByIds: function(speakerIds, callback) {
            var result = [];
            checkForItem(SPEAKER, function(speakers) {
                for (var i in speakers) {
                    var speaker = speakers[i];
                    if (speakerIds.indexOf(speaker.id) !== -1) {
                        result.push(speaker);
                    }
                }
            });

            callback(result);
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
        },

        findCurrentSessions: function(callback) {
            var currentTimestamp = new Date().getTime();
            checkForItem(SESSION, function(days) {
                var result = [];
                for (var i in days) {
                    var sessions = days[i].sessions;
                    for (var j in sessions) {
                        var session = sessions[j];
                        if (session.startTime > currentTimestamp && session.endTime < currentTimestamp) {
                            result.push(session);
                        }
                    }
                }

                callback(result);
            });
        }
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

    // the string '[]' has length of 2, so there is no data, just an empty array
    if (data === null || data.length === 2) {
        restCall(itemName + '/all', function(result) {
            localStorage.setItem(itemName, JSON.stringify(result));

            localStorage.setItem(LAST_UPDATE, new Date().getTime());
            callback(result);
        });
    }

    else {
        var lastUpdate = localStorage.getItem(LAST_UPDATE);
        if (lastUpdate != null) {
            var lastUpdateTime = new Date(new Number(lastUpdate)).getTime();

            //12 hours
            if (new Date().getTime() - lastUpdateTime > 1000 * 60 * 60 * 12) {
                restCall(itemName + '/all', function(result) {
                    localStorage.setItem(itemName, JSON.stringify(result));
                    localStorage.setItem(LAST_UPDATE, new Date().getTime());
                    callback(result);
                });
            }

            else {
                callback(JSON.parse(data));
            }
        }

        else {
            localStorage.setItem(LAST_UPDATE, new Date().getTime());
            callback(JSON.parse(data));
        }
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