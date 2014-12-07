(function() {
	// TODO: used and created by the ionic starter application, still have to check what it does exactly
	var runFunction = function($ionicPlatform) {
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
	
	var Config = function($stateProvider, $urlRouterProvider) {
		$stateProvider.state('app', {
			abstract: true,
			templateUrl: "templates/layout.html",
			controller: 'MenuCtrl as menuCtrl'
	    });

	    $stateProvider.state('app.twitter', {
	        url: "/twitter",
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
	        },
	        resolve: {
	        	conferenceDays: ['SessionService', function(sessionService) {
	        		return sessionService.findAll();
	        	}]
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
	    	url: '/map',
	    	views: {
	    		'content': {
	    			templateUrl: 'templates/map.html',
	    			controller: 'MapCtrl as mapCtrl'
	    		}
	    	}
	    });
	    
	    $urlRouterProvider.otherwise('/twitter');
	}
	
	angular.module('ehfgApp', ['ionic', 'ngResource', 'ehfgApp.twitter', 'ehfgApp.menu', 'ehfgApp.speakers', 'ehfgApp.sessions', 'ehfgApp.map'])
		.config(['$stateProvider', '$urlRouterProvider', Config])
		.run(['$ionicPlatform', runFunction])
})();
