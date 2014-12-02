angular.module('ehfgApp.map', ['uiGmapgoogle-maps'])

.controller('MapCtrl', ['$scope', function($scope) {
	$scope.map = {
			center: {
				latitude: 123,
				longitude: 123
			},
			zoom: 8
	}
}])

function onGoogleReady() {
	angular.bootstrap(document.getElementById("map"), ['uiGmapgoogle-maps']);
}