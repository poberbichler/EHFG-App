angular.module('ehfgApp', ['ionic', 'ngResource', 'ehfgApp.twitter', 'ehfgApp.menu', 'ehfgApp.speakers', 'ehfgApp.sessions'])

// used by the ionic starter application, still have to check what it does
.run(function($ionicPlatform) {
    $ionicPlatform.ready(function() {
        // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
        // for form inputs)
        if(window.cordova && window.cordova.plugins.Keyboard) {
            cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
        }

        if(window.StatusBar) {
            // org.apache.cordova.statusbar required
            StatusBar.styleDefault();
        }
    });
})

.config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('app', {
      abstract: true,
      templateUrl: "templates/menu.html",
      controller: 'MenuCtrl'
    });

    $stateProvider.state('app.twitter', {
        url: "/twitter",
        views: {
            'menuContent': {
                templateUrl: 'templates/twitter.html',
                controller: 'TwitterCtrl'
            }
        }
    });

    $stateProvider.state('app.speakers', {
        url: "/speakers",
        views: {
            'menuContent': {
                templateUrl: 'templates/speakers.html',
                controller: 'SpeakerCtrl'
            }
        }
    });
    
    $stateProvider.state('app.speakers.detail', {
    	url: '/:speakerId',
    	views: {
    		'menuContent@app': {
    			templateUrl: 'templates/speaker-detail.html',
    			controller: 'SpeakerDetailCtrl'
    		}
    	}
    });

    $stateProvider.state('app.sessions', {
        url: '/sessions',
        views: {
            'menuContent': {
                templateUrl: 'templates/sessions.html',
                controller: 'SessionCtrl'
            }
        }
    });

    $stateProvider.state('app.sessions.detail', {
       url: '/:sessionId',
       views: {
           'menuContent@app': {
               templateUrl: 'templates/session-detail.html',
               controller: 'SessionDetailCtrl'
           }
       }
    });
    
    $urlRouterProvider.otherwise('/twitter');
})