(function() {
    function SessionService($q, sessionResource, localStorageService) {
        return {
            findAll: findAll,
            findById: findById,
            findBySpeakerId: findBySpeakerId,
            findCurrentSessions: findCurrentSessions,

            showAllSessions: showAllSessions,
            showFavouriteSessions: showFavouriteSessions,
            getFavouriteSessionFlag: getFavouriteSessionFlag,

            isFavouriteSession: isFavouriteSession,
            findFavouriteSessions: findFavouriteSessions,
            addToFavourites: addToFavourites,
            removeFromFavourites: removeFromFavourites
        }

        function findAll() {
            var storage = localStorageService.findSessions();
            if (storage.length === 0) {
                return sessionResource.findAll(function(data) {
                    localStorageService.setSessions(data);
                }).$promise;
            }

            return $q.when(storage);
        }

        function findById(sessionId) {
            return this.findAll().then(function(conferenceDays) {
                for (var i in conferenceDays) {
                    var day = conferenceDays[i];
                    for (var j in day.sessions) {
                        var session = day.sessions[j];

                        if (session.id == sessionId) {
                            return $q.when(session);
                        }
                    }
                }

                return null;
            });
        }

        function findBySpeakerId(speakerId) {
            return this.findAll().then(function(conferenceDays) {
                var result = [];
                for (var i in conferenceDays) {
                    var day = conferenceDays[i];

                    for (var j in day.sessions) {
                        var session = day.sessions[j];
                        for (var speaker in session.speakers) {
                            if (session.speakers[speaker] === speakerId) {
                                result.push(session);
                                break;
                            }
                        }
                    }
                }

                return $q.when(result);
            });
        }

        function findCurrentSessions() {
            return this.findAll().then(function(conferenceDays) {
                var result = [];

                // use utc timestamp
                var currentDate = new Date();
                currentDate.setMinutes(date.getMinutes() + date.getTimezoneOffset());
                var now = currentDate.getTime();

                for (var i in conferenceDays) {
                    var day = conferenceDays[i];

                    for (var j in day.sessions) {
                        var session = day.sessions[j];

                        if (session.start <= now && now <= session.end) {
                            result.push(session);
                        }
                    }
                }

                return $q.when(result);
            });
        }

        function showAllSessions() {
            localStorageService.setFavouriteSessionSelected(false);
        }

        function showFavouriteSessions() {
            localStorageService.setFavouriteSessionSelected(true);
        }

        function getFavouriteSessionFlag() {
            return localStorageService.showFavouriteSessionsSelected();
        }

        function findFavouriteSessions() {
            return localStorageService.findFavouriteSessions();
        }

        function isFavouriteSession(sessionId) {
            return this.findFavouriteSessions().indexOf(sessionId) !== -1;
        }

        function addToFavourites(sessionId) {
            localStorageService.addToFavouriteSessions(sessionId);
        }

        function removeFromFavourites(sessionId) {
            localStorageService.removeFromFavouriteSessions(sessionId);
        }
    }

    angular.module('ehfgApp.sessions')
        .factory('SessionService', ['$q', 'SessionResource', 'LocalStorageService', SessionService])
})();