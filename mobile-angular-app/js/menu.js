angular.module('ehfgApp.menu', [])

.controller('MenuCtrl', ['$scope', '$state', function($scope, $state) {
    $scope.checkActive = function(checkParameter) {
        console.log($state, checkParameter);
        return $state.current.data.menuHighlight === checkParameter;
    }
}])