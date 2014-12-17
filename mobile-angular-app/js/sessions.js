(function() {
	var SessionCtrl = function(conferenceDays) {
		this.conferenceDays = conferenceDays;
	}
	
	var SessionDetailCtrl = function($scope, $stateParams, sessionService, speakerService) {
		$scope.addToFavourites = sessionService.addToFavourites;
		$scope.removeFromFavourites = sessionService.removeFromFavourites;
		
		sessionService.findById($stateParams.sessionId).then(function(session) {
	        $scope.session = session;
	        $scope.session.isInFavourites = sessionService.isFavouriteSession(session.id);
	
	        speakerService.findByIds(session.speakers).then(function(speakers) {
	        	$scope.speakers = speakers;
	        });
	    });
		
		$scope.addToFavourites = function() {
			$scope.session.isInFavourites = true;
			sessionService.addToFavourites($scope.session.id);
		}
		
		$scope.removeFromFavourites = function() {
			$scope.session.isInFavourites = false;
			sessionService.removeFromFavourites($scope.session.id);
		}
	}
	
	var FavouriteSessionFilter = function(sessionService) {
		return function(items) {
			if (sessionService.getFavouriteSessionFlag() === false) {
				return items;
			}
			
			var result = [];
			var favouriteSessions = sessionService.findFavouriteSessions();
			
			for (var i in items) {
				var item = items[i];
				if (favouriteSessions.indexOf(item.id) !== -1) {
					result.push(item);
				}
			}

			return result;
		}
	}
	
	var SessionResource = function($resource, BASE_URL) {
		return $resource(BASE_URL + '/session/all?callback=JSON_CALLBACK', {}, {
			findAll: {method: 'JSONP', isArray: true}
		});
	}
	
	var SessionService = function($q, sessionResource, localStorageService) {
	    return {
	    	findAll: findAll,
	    	findById: findById,
	    	findBySpeakerId: findBySpeakerId,
	    	
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
	
	angular.module('ehfgApp.sessions', [])
		.controller('SessionCtrl', ['conferenceDays', SessionCtrl])
		.controller('SessionDetailCtrl', ['$scope', '$stateParams','SessionService', 'SpeakerService', SessionDetailCtrl])
		.filter('favouriteSessions', ['SessionService', FavouriteSessionFilter])
		.factory('SessionService', ['$q', 'SessionResource', 'LocalStorageService', SessionService])
		.factory('SessionResource', ['$resource', 'BASE_URL', SessionResource])
})();