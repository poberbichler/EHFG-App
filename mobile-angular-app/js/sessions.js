(function() {
	var SessionCtrl = function(sessionService) {
		var vm = this;
		sessionService.findAll().then(function(conferenceDays) {
			vm.conferenceDays = conferenceDays;
		});

		sessionService.findCurrentSessions().then(function(sessions) {
			vm.currentSessions = sessions;
		});
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
		return function(item) {
			if (sessionService.getFavouriteSessionFlag() === false) {
				return true;
			}

			return sessionService.findFavouriteSessions().indexOf(item.id) !== -1;
		}
	}
	
	var SessionResource = function($resource, BASE_URL) {
		return $resource(BASE_URL + '/session/all?callback=JSON_CALLBACK', {}, {
			findAll: {method: 'JSONP', isArray: true}
		});
	}
	
	angular.module('ehfgApp.sessions', [])
		.controller('SessionCtrl', ['SessionService', SessionCtrl])
		.controller('SessionDetailCtrl', ['$scope', '$stateParams','SessionService', 'SpeakerService', SessionDetailCtrl])
		.filter('favouriteSessions', ['SessionService', FavouriteSessionFilter])
		.factory('SessionResource', ['$resource', 'BASE_URL', SessionResource])
})();