angular.module('ehfgApp.menu', [])

.controller('MenuCtrl', ['$scope', '$ionicSideMenuDelegate', '$ionicPopup', '$state', 'SessionService',
    function($scope, $ionicSideMenuDelegate, $ionicPopup, $state, sessionService) {
	
	$scope.favouriteSessions = sessionService.getFavouriteSessionFlag();
	$scope.$watch('favouriteSessions', function(newValue, oldValue) {
		if (newValue !== oldValue) {
			console.log('setting favouriteSessionFlag to...', newValue);
			if (newValue === true) {
				sessionService.showFavouriteSessions();
			}
			
			else {
				sessionService.showAllSessions();
			}
		}
	});
	
	$scope.openSideMenu = function() {
		$ionicSideMenuDelegate.toggleRight();
	}
	
	$scope.showAboutDialog = function() {
		var aboutDialog = $ionicPopup.show({
			template: 
				'<p>The European Health Forum Gastein is an annual international conference where stakeholders within'
	            + 'the field of healthcare and public health meet to discuss a broad spectrum of major topics.</p>'
	            + '<p>The theme of this year is "Electing Health-The Europe We Want."</p>'
	            + '<p>Please direct any questions or concerns to <a href="mailto:info@ehfg.org">info@ehfg.org</a></p>',
			title: 'About',
			buttons: [{text: 'Exit'}]
		});
	}
	
	$scope.resetData = function() {
		localStorage.clear();
	}
}])
