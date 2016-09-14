(function() {
	function MenuCtrl($ionicSideMenuDelegate, $ionicPopup, $state, $window, cacheFactory, favouriteSessionService) {
		var vm = this;

		vm.openSideMenu = function() {
            $ionicSideMenuDelegate.toggleRight();
        }

		vm.showAboutDialog = function() {
			$ionicPopup.show({
				template: 
					'<p>The European Health Forum Gastein is an annual international conference where stakeholders within '
		            + 'the field of healthcare and public health meet to discuss a broad spectrum of important topics.</p>'
		            + '<p>The theme of this year is "Demographics and Diversity in Europe - New Solutions for Health"</p>'
		            + '<p>Please direct any questions or concerns to <a href="mailto:info@ehfg.org">info@ehfg.org</a></p>',
				title: 'About',
				buttons: [{text: 'Exit'}]
			});
		}

		vm.search = function() {
            $ionicSideMenuDelegate.toggleRight();
            $state.go('app.search', {searchParam: vm.searchInput}, {reload: true});
			vm.searchInput = '';
        }

		vm.resetData = function() {
            $state.go('app.twitter');
            window.setTimeout(function() {
                cacheFactory.clearAll();
                $window.location.reload();
            }, 250);
		}

		vm.evaluateKey = function($event) {
			if ($event.keyCode === 13) {
				vm.search();
			}
		}
	}

	angular.module('ehfgApp.menu', [])
		.controller('MenuCtrl', ['$ionicSideMenuDelegate', '$ionicPopup', '$state', '$window', 'CacheFactory', MenuCtrl])
})();