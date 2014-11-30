angular.module('ehfgApp.menu', [])

.controller('MenuCtrl', ['$scope', '$ionicSideMenuDelegate', 'SessionService', 
    function($scope, $ionicSideMenuDelegate, sessionService) {
	
	$scope.favouriteSessions = true;
	$scope.$watch('favouriteSessions', function(newValue, oldValue) {
		if (newValue !== oldValue) {
			if (newValue == true) {
				sessionService.showAllSessions();
			}
			
			else {
				sessionService.showFavouriteSessions();
			}
		}
	});
	
	$scope.openSideMenu = function() {
		$ionicSideMenuDelegate.toggleRight();
	}
}])
