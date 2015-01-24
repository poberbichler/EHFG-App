(function() {
	// created by the ionic starter application
	function RunFunction($ionicPlatform) {
		$ionicPlatform.ready(function() {
			// Hide the accessory bar by default (remove this to show the accessory bar above the keyboard for form inputs)
			if(window.cordova && window.cordova.plugins.Keyboard) {
				cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
			}
			
			if(window.StatusBar) {
				// org.apache.cordova.statusbar required
				StatusBar.styleDefault();
			}
		});
	}
	
	function Config($stateProvider, $urlRouterProvider, $ionicConfigProvider) {
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
	        url: "/speakers",
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
	                controller: 'SessionCtrl as sessionCtrl',
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
	    		}
	    	},
	    	
	    	resolve: {
	    		highlightLocation: ['$stateParams', 'MapService', function($stateParams, mapService) {
	    			if ($stateParams.location !== undefined && $stateParams.location.length !== 0) {
	    				return mapService.locations.findByName({name: $stateParams.location}).$promise;
	    			}
	    			
	    			return null;
	    		}]
	    	}
	    });
	    
	    $urlRouterProvider.otherwise('/twitter');
	    $ionicConfigProvider.views.transition('android');
	}
	
	function InitBackdoor($http, BASE_URL) {
		$http.jsonp(BASE_URL + '/backdoor?callback=JSON_CALLBACK');
	}
	
	angular.module('ehfgApp', [
		'ionic',
		'ngResource',
		'ehfgApp.twitter',
		'ehfgApp.menu',
		'ehfgApp.speakers',
		'ehfgApp.sessions',
		'ehfgApp.map',
		'ehfgApp.storage',
		'ehfgApp.config'
	]).config(['$stateProvider', '$urlRouterProvider', '$ionicConfigProvider', Config])
        .run(['$ionicPlatform', RunFunction])
        .run(['$http', 'BASE_URL', InitBackdoor])
})();
