(function() {
	function MenuCtrl($ionicSideMenuDelegate, $ionicPopup, $state, $window, cacheFactory, configurationService) {
		this.favouriteSessions = configurationService.isFavouriteSessionsSelected();
		this.favouriteSessionToggle = configurationService.toggleFavouriteSessions;

        this.openSideMenu = function() {
            $ionicSideMenuDelegate.toggleRight();
        }

		this.showAboutDialog = function() {
			$ionicPopup.show({
				template: 
					'<p>The European Health Forum Gastein is an annual international conference where stakeholders within '
		            + 'the field of healthcare and public health meet to discuss a broad spectrum of important topics.</p>'
		            + '<p>The theme of this year is "Securing health in Europe. Balancing priorities, sharing responsibilities"</p>'
		            + '<p>Please direct any questions or concerns to <a href="mailto:info@ehfg.org">info@ehfg.org</a></p>',
				title: 'About',
				buttons: [{text: 'Exit'}]
			});
		}
		
		this.resetData = function() {
            $state.go('app.twitter');
            cacheFactory.clearAll();
            $window.location.reload();
		}
	}

    function ConfigurationService(cacheFactory) {
        var configCache = cacheFactory.get('config');
        if (!configCache) {
            configCache = new cacheFactory('config', {
                deleteOnExpire: 'none'
            });
        }

        if (configCache.keys().length === 0) {
            configCache.put('favourite.sessions', []);
            configCache.put('show.favourite.sessions', false);
        }

        return {
            isFavouriteSessionsSelected: isFavouriteSessionsSelected,
            toggleFavouriteSessions: toggleFavouriteSessions
        }

        function isFavouriteSessionsSelected() {
            return configCache.get('show.favourite.sessions');
        }

        function toggleFavouriteSessions() {
            var currentValue = isFavouriteSessionsSelected();
            if (currentValue) {
                configCache.put('show.favourite.sessions', false);
            } else {
                configCache.put('show.favourite.sessions', true);
            }
        }
    }

	angular.module('ehfgApp.menu', [])
		.controller('MenuCtrl', ['$ionicSideMenuDelegate', '$ionicPopup', '$state', '$window', 'CacheFactory', 'ConfigurationService', MenuCtrl])
        .factory('ConfigurationService', ['CacheFactory', ConfigurationService]);
})();