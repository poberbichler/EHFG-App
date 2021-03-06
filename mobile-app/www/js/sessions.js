(function() {
	function SessionCtrl($location, $ionicScrollDelegate, $filter, sessionService, favouriteSessionService) {
        this.conferenceDays = sessionService.findAll();
        this.currentSessions = sessionService.findCurrentSessions();

        this.favouriteSessions = favouriteSessionService.isFavouriteSessionsSelected();
        this.favouriteSessionToggle = favouriteSessionService.toggleFavouriteSessions;

        var currentDayString = $filter('date')(new Date(), 'yyyy-MM-dd');
        angular.forEach(this.conferenceDays, function(value, key) {
            if (key === currentDayString) {
                $location.hash(value.description);
                $ionicScrollDelegate.anchorScroll(true);
            }
        });
	}
	
	function SessionDetailCtrl($scope, $stateParams, sessionService, favouriteSessionService, speakerService) {
		$scope.addToFavourites = sessionService.addToFavourites;
		$scope.removeFromFavourites = sessionService.removeFromFavourites;
		
		$scope.session = sessionService.findById($stateParams.sessionId);
        $scope.session.isInFavourites = favouriteSessionService.isFavouriteSession($stateParams.sessionId);
        $scope.speakers = speakerService.findByIds($scope.session.speakers);

		$scope.addToFavourites = function() {
			$scope.session.isInFavourites = true;
            favouriteSessionService.addToFavourites($scope.session.id);
		}
		
		$scope.removeFromFavourites = function() {
			$scope.session.isInFavourites = false;
            favouriteSessionService.removeFromFavourites($scope.session.id);
		}
	}
	
	function FavouriteSessionFilter(favouriteSessionService) {
		return function(item) {
			if (favouriteSessionService.isFavouriteSessionsSelected() === false) {
				return true;
			}

            return favouriteSessionService.isFavouriteSession(item.id);
		}
	}
	
	function SessionResource($resource, BASE_URL) {
		return $resource(BASE_URL + '/sessions', {}, {
			findAll: {method: 'GET', isArray: false}
		});
	}
	
	angular.module('ehfgApp.sessions', [])
		.controller('SessionCtrl', ['$location', '$ionicScrollDelegate', '$filter', 'SessionService', 'FavouriteSessionService', SessionCtrl])
		.controller('SessionDetailCtrl', ['$scope', '$stateParams','SessionService', 'FavouriteSessionService', 'SpeakerService', SessionDetailCtrl])
		.filter('favouriteSessions', ['FavouriteSessionService', FavouriteSessionFilter])
		.factory('SessionResource', ['$resource', 'BASE_URL', SessionResource])
})();