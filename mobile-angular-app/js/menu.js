(function() {
	var MenuCtrl = function($ionicSideMenuDelegate, $ionicPopup, sessionService) {
		this.favouriteSessions = sessionService.getFavouriteSessionFlag();
		
		this.favouriteSessionToggle = function(newValue, ctrl) {
			if (newValue === true) {
				sessionService.showFavouriteSessions();
			}
			
			else {
				sessionService.showAllSessions();
			}
		}
		
		this.openSideMenu = function() {
			$ionicSideMenuDelegate.toggleRight();
		}
		
		this.showAboutDialog = function() {
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
		
		this.resetData = function() {
			localStorage.clear();
		}
	}
	
	angular.module('ehfgApp.menu', [])
		.controller('MenuCtrl', ['$ionicSideMenuDelegate', '$ionicPopup', 'SessionService', MenuCtrl]);
})();