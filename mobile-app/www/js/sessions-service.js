(function() {
    function SessionService(cacheFactory, sessionResource) {
        var sessionCache = cacheFactory.get('sessions');

        if (!sessionCache) {
            sessionCache = new cacheFactory('sessions', {
               onExpire: initCache
            });

            if (sessionCache.keys().length === 0) {
                initCache('all');
            }
        }

        function initCache(key) {
            if (key === 'all') {
                sessionCache.put('all', sessionResource.findAll(function(conferenceDays) {
                    for (var i in conferenceDays) {
                        var day = conferenceDays[i];
                        for (var j in day.sessions) {
                            var session = day.sessions[j];
                            sessionCache.put(session.id, session);
                        }
                    }
                }).$promise);
            }
        }

        return {
            findAll: findAll,
            findById: findById,
            findBySpeakerId: findBySpeakerId,
            findCurrentSessions: findCurrentSessions
        }

        function findAll() {
            return sessionCache.get('all');
        }

        function findById(sessionId) {
            return sessionCache.get(sessionId);
        }

        function findBySpeakerId(speakerId) {
            var result = [];

            var conferenceDays = this.findAll();
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

            return result;
        }

        function findCurrentSessions() {
            var conferenceDays = this.findAll();
            var result = [];

            // use utc timestamp
            var currentDate = new Date();
            currentDate.setMinutes(currentDate.getMinutes() + currentDate.getTimezoneOffset());
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
        }
    }

    function FavouriteSessionService(cacheFactory) {
        var favouriteSessionCache = cacheFactory.get('favourite');
        if (!favouriteSessionCache) {
            favouriteSessionCache = new cacheFactory('favourite', {
                deleteOnExpire: 'none'
            });
        }

        if (favouriteSessionCache.keys().length === 0) {
            favouriteSessionCache.put('sessions', []);
            favouriteSessionCache.put('show', false);
        }

        return {
            isFavouriteSessionsSelected: isFavouriteSessionsSelected,
            toggleFavouriteSessions: toggleFavouriteSessions,
            findFavouriteSessions: findFavouriteSessions,
            isFavouriteSession: isFavouriteSession,
            addToFavourites: addToFavourites,
            removeFromFavourites: removeFromFavourites
        }

        function isFavouriteSessionsSelected() {
            return favouriteSessionCache.get('show');
        }

        function toggleFavouriteSessions() {
            var currentValue = isFavouriteSessionsSelected();
            if (currentValue) {
                favouriteSessionCache.put('show', false);
            } else {
                favouriteSessionCache.put('show', true);
            }
        }

        function findFavouriteSessions() {
            return favouriteSessionCache.get('sessions');
        }

        function isFavouriteSession(sessionId) {
            var favouriteSessions = this.findFavouriteSessions();
            if (!favouriteSessions) {
                return false;
            }
            return favouriteSessions.indexOf(sessionId) !== -1;
        }

        function addToFavourites(sessionId) {
            var favouriteSessions = this.findFavouriteSessions();
            favouriteSessions.push(sessionId);
            favouriteSessionCache.put('sessions', favouriteSessions);
        }

        function removeFromFavourites(sessionId) {
            var favouriteSessions = this.findFavouriteSessions();
            var index = favouriteSessions.indexOf(sessionId);

            if (index !== -1) {
                favouriteSessions.splice(index);
                favouriteSessionCache.put('sessions', favouriteSessions);
            }
        }
    }

    angular.module('ehfgApp.sessions')
        .factory('SessionService', ['CacheFactory', 'SessionResource', SessionService])
        .factory('FavouriteSessionService', ['CacheFactory', FavouriteSessionService]);
})();