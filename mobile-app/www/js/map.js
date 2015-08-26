(function() {
	function MapCtrl($scope, mapService, highlightLocation) {
		var vm = this;

		$scope.$on('$ionicView.enter', function(event, view) {
			vm.zoom = 16;

			if (!vm.points) {
				vm.points = mapService.points.findAll();
			}

			if (highlightLocation === null) {
				vm.center = {latitude: 47.170329, longitude: 13.103852}
			}  else {
				vm.highlightLocation = highlightLocation;
				// copy the properties, otherwise the center location of 'hightlightLocation' will be changed
				vm.center = {
					latitude: highlightLocation.coordinate.latitude,
					longitude: highlightLocation.coordinate.longitude
				}
			}
		});
	}
	
	function MapService($resource, BASE_URL) {
		return {
			locations: new $resource(BASE_URL + '/locations/:name', {}, {
					findByName: {method: 'GET', params: {name: '@name'}}
				}),
			
			points: new $resource(BASE_URL + '/points', {}, {
                findAll: {method: 'GET', isArray: true, transformResponse: function(rawData) {
                    var data = angular.fromJson(rawData);
                    for (var i in data) {
                        data[i].icon = 'img/marker.png';
                    }
                    return data;
                }}
            })
		}
	}

	angular.module('ehfgApp.map', ['uiGmapgoogle-maps'])
		.controller('MapCtrl', ['$scope', 'MapService', 'highlightLocation', MapCtrl])
		.factory('MapService', ['$resource', 'BASE_URL', MapService])
})();

function onGoogleReady() {
	angular.bootstrap(document.getElementById("map"), ['uiGmapgoogle-maps']);
}