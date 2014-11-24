angular.module('ehfgApp.speakers', [])

.controller('SpeakerCtrl', ['$scope', 'SpeakerService', function($scope, speakerService) {
    speakerService.findAll().then(function(data) {
        $scope.speakers = data;
    });
}])

.controller('SpeakerDetailCtrl', ['$scope', '$stateParams', 'SpeakerService', 'SessionService', 
    function($scope, $stateParams, speakerService, sessionService) {
	
	speakerService.findById($stateParams.speakerId).then(function(data) {
		$scope.speaker = data;
	});
	
	sessionService.findBySpeakerId($stateParams.speakerId).then(function(data) {
		$scope.sessions = data;
	});
}])


.factory('SpeakerService', ['$http', '$q', function($http, $q) {
    var SPEAKER_STORAGE = "SPEAKERS";

    return {
        findAll: function() {
            localStorage.clear();
            var result = $q.defer();

            var storage = JSON.parse(localStorage.getItem(SPEAKER_STORAGE));
            if (storage === null || storage.length === 0) {
                $http.jsonp('http://localhost:8080/rest/speaker/all?callback=JSON_CALLBACK')
                    .success(function(data, status) {
                        localStorage.setItem(SPEAKER_STORAGE, JSON.stringify(data));
                        result.resolve(data);
                    }
                );
            }

            else {
                result.resolve(storage);
            }

            return result.promise;
        },

        findByIds: function(speakerIds) {
            var endResult = $q.defer();
            
            return this.findAll().then(function(speakers) {
                var result = [];
                for (var speakerId in speakerIds) {
                    for (var i in speakers) {
                        if (speakers[i].id == speakerIds[speakerId]) {
                            result.push(speakers[i]);
                        }
                    }
                }

                endResult.resolve(result);
                return endResult.promise;
            });
        },
        
        findById: function(speakerId) {
        	var result = $q.defer();
        	
        	result.resolve(this.findAll().then(function(speakers) {
        		for (var i in speakers) {
        			if (speakers[i].id === speakerId) {
        				return speakers[i];
        			}
        		}
        		
        		return null;
        	}));
        	
        	return result.promise;
        }
    }
}])