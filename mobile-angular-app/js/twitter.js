angular.module('ehfgApp.twitter', [])

.controller('TwitterCtrl', ['$scope', function($scope) {
    $scope.tweets = [{id: 4, text: 'Hallo Welt'},
        {id: 5, text: 'test123'}];
    console.log('tweets', $scope.tweets);
}])
