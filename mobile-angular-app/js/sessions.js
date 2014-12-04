(function() {
	var SessionCtrl = function(conferenceDays) {
		this.conferenceDays = conferenceDays;
	}
	
	var SessionDetailCtrl = function($scope, $stateParams, sessionService, speakerService) {
		$scope.addToFavourites = sessionService.addToFavourites;
		$scope.removeFromFavourites = sessionService.removeFromFavourites;

		sessionService.findById($stateParams.sessionId).then(function(session) {
	        $scope.session = session;
	
	        speakerService.findByIds(session.speakers).then(function(speakers) {
	        	$scope.speakers = speakers;
	        });
	    });
	}
	
	var FavouriteSessionFilter = function(sessionService) {
		return function(items) {
			if (sessionService.getFavouriteSessionFlag() === false) {
				return items;
			}
			
			var result = [];
			var favouriteSessions = sessionService.findFavouriteSessions();
			
			for (i in items) {
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
	            var result = $q.defer();
	
	            var storage = JSON.parse(localStorage.getItem(SESSION_STORAGE));
	            if (storage === null || storage.length === 0) {
	                $http.jsonp(BASE_URL + '/session/all?callback=JSON_CALLBACK')
	                    .success(function(data, status) {
	                        localStorage.setItem(SESSION_STORAGE, JSON.stringify(data));
	                        result.resolve(data);
	                    }
	                );
	            }
	
	            else {
	                result.resolve(storage);
	            }
	
	            return result.promise;
	        },
	
	        findById: function(sessionId) {
	            var result = $q.defer();
	
	            result.resolve(this.findAll().then(function(conferenceDays) {
	                for (var i in conferenceDays) {
	                    var day = conferenceDays[i];
	                    for (var j in day.sessions) {
	                        var session = day.sessions[j];
	
	                        if (session.id == sessionId) {
	                            return session;
	                        }
	                    }
	                }
	
	                return null;
	            }));
	
	            return result.promise;
	        },
	        
	        findBySpeakerId: function(speakerId) {
	        	var endResult = $q.defer();
	        	endResult.resolve(this.findAll().then(function(conferenceDays) {
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
	        		
	        		return result;
	        	}));
	        	
	        	return endResult.promise;
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
	        
	        findFavouriteSessions: function() {
	        	return JSON.parse(localStorage.getItem(FAVOURITE_SESSIONS)) || [];
	        },
	        
	        addToFavourites: function(sessionId) {
	        	console.log(this);//.push(sessionId);
	        },
	        
	        removeFromFavourites: function(sessionId) {
	        	this.findFavouriteSessions().splice(sessionId, 1);
	        }
	    }
	}
	
	angular.module('ehfgApp.sessions', [])
		.controller('SessionCtrl', ['conferenceDays', SessionCtrl])
		.controller('SessionDetailCtrl', ['$scope', '$stateParams','SessionService', 'SpeakerService', SessionDetailCtrl])
		.filter('favouriteSessions', ['SessionService', FavouriteSessionFilter])
		.factory('SessionService', ['$http', '$q', SessionService])
})();