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
	
	//TODO: try to find a better solution. actually using a filter is ok, becase there are not that many sessions...  
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
	
	var SessionService = function($http, $q) {
	    var SESSION_STORAGE = 'SESSIONS';
	    var FAVOURITE_SESSIONS = 'FAVOURITE_SESSIONS';
	    var SHOW_FAVOURITE_SESSIONS = 'SHOW_FAVOURITE_SESSIONS';
	    
	    return {
	        findAll: function() {
	            var storage = JSON.parse(localStorage.getItem(SESSION_STORAGE));
	            if (storage === null || storage.length === 0) {
	            	var result = $q.defer();
	                $http.jsonp(BASE_URL + '/session/all?callback=JSON_CALLBACK')
	                    .success(function(data, status) {
	                        localStorage.setItem(SESSION_STORAGE, JSON.stringify(data));
	                        result.resolve(data);
	                    }
	                );
	                
	                return result.promise;
	            }
	            
	            return $q.when(storage);
	        },
	
	        findById: function(sessionId) {
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
	        },
	        
	        findBySpeakerId: function(speakerId) {
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
	        },
	        
	        showAllSessions: function() {
	        	if (this.getFavouriteSessionFlag() == true) {
	        		localStorage.setItem(SHOW_FAVOURITE_SESSIONS, false);
	        	}
	        },
	        
	        showFavouriteSessions: function() {
	        	if (this.getFavouriteSessionFlag() == false) {
	        		localStorage.setItem(SHOW_FAVOURITE_SESSIONS, true);
	        	}
	        },
	        
	        getFavouriteSessionFlag: function() {
	        	var result = localStorage.getItem(SHOW_FAVOURITE_SESSIONS);
	        	if (result === null) {
	        		localStorage.setItem(SHOW_FAVOURITE_SESSIONS, 'false');
	        		return false;
	        	}
	        	
	        	return result === 'true';
	        },
	        
	        isFavouriteSession: function(sessionId) {
	        	return this.findFavouriteSessions().indexOf(sessionId) !== -1;
	        },
	        
	        findFavouriteSessions: function() {
	        	return JSON.parse(localStorage.getItem(FAVOURITE_SESSIONS)) || [];
	        },
	        
	        addToFavourites: function(sessionId) {
	        	var favourites = this.findFavouriteSessions();
	        	favourites.push(sessionId);
	        	localStorage.setItem(FAVOURITE_SESSIONS, JSON.stringify(favourites));
	        },
	        
	        removeFromFavourites: function(sessionId) {
	        	var favourites = this.findFavouriteSessions();
	        	favourites.splice(sessionId, 1);
	        	localStorage.setItem(FAVOURITE_SESSIONS, JSON.stringify(favourites));
	        }
	    }
	}
	
	angular.module('ehfgApp.sessions', [])
		.controller('SessionCtrl', ['conferenceDays', SessionCtrl])
		.controller('SessionDetailCtrl', ['$scope', '$stateParams','SessionService', 'SpeakerService', SessionDetailCtrl])
		.filter('favouriteSessions', ['SessionService', FavouriteSessionFilter])
		.factory('SessionService', ['$http', '$q', SessionService])
})();