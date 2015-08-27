(function() {
	function MapCtrl($scope, mapService, highlightLocation) {
		var vm = this;

		$scope.$on('$ionicView.enter', function(event, view) {
			vm.zoom = 16;

			if (!vm.points) {
				vm.points = mapService.points.findAll();
			}

            vm.center = {latitude: 47.170329, longitude: 13.103852}
			if (highlightLocation !== null) {
				vm.points.$promise.then(function(data) {
					angular.forEach(data, function(point, index) {
						if (point.name && point.name === highlightLocation.name) {
							point.icon = 'img/highlightmarker.png';
							vm.center = {
								latitude: point.coordinate.latitude,
								longitude: point.coordinate.longitude
							}
						}
					});
				});
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