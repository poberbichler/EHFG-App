angular.module('ehfgApp.speakers', [])

.controller('SpeakerCtrl', ['$scope', 'SpeakerService', function($scope, speakerService) {
    speakerService.findAll().then(function(data) {
        $scope.speakers = data;
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
                        if (speakers[i].id == speakerId) {
                            result.push(speakers[i]);
                        }
                    }
                }

                return endResult.promise;
            });
        }
    }
}])