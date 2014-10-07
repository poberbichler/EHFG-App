angular.module('ehfgApp.sessions', [])

.controller('SessionCtrl', ['$scope', 'SessionService', function($scope, sessionService) {
    sessionService.findAll().then(function(conferenceDays) {
        console.log(conferenceDays);
        $scope.conferenceDays = conferenceDays;
    })
}])

.controller('SessionDetailCtrl', ['$scope', '$stateParams','SessionService', 'SpeakerService',
    function($scope, $stateParams, sessionService, speakerService) {

    sessionService.findById($stateParams.sessionId).then(function(session) {
        $scope.session = session;

        speakerService.findByIds(session.speakers).then(function(speakers) {
           $scope.speakers = speakers;
        });
    });
}])

.factory('SessionService', function($http, $q) {
    var SESSION_STORAGE = 'SESSIONS';

    return {
        findAll: function() {
            var result = $q.defer();

            var storage = JSON.parse(localStorage.getItem(SESSION_STORAGE));
            if (storage === null || storage.length === 0) {
                $http.jsonp('http://localhost:8080/rest/session/all?callback=JSON_CALLBACK')
                    .success(function(data, status) {
                        localStorage.setItem(SESSION_STORAGE, JSON.stringify(data));
                        result.resolve(data);
                    }
                );
            }

            else {
                result.resolve(storage);
            }

            return result.promise;
        },

        findById: function(sessionId) {
            var result = $q.defer();

            result.resolve(this.findAll().then(function(conferenceDays) {
                for (var i in conferenceDays) {
                    var day = conferenceDays[i];
                    for (var j in day.sessions) {
                        var session = day.sessions[j];

                        if (session.id == sessionId) {
                            return session;
                        }
                    }
                }

                return null;
            }));

            return result.promise;
        }
    }
})