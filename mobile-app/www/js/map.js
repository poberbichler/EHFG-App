(function() {
	function MapCtrl($scope, mapService, points, highlightLocation) {
		var vm = this;
        vm.points = points;

		$scope.$on('$ionicView.enter', function() {
			vm.zoom = 16;
            vm.center = {latitude: 47.170329, longitude: 13.103852};
			if (highlightLocation) {
                if (highlightLocation.pointId) {
                    vm.points.$promise.then(function(data) {
                        angular.forEach(data, function(point, index) {
                            if (point.id && point.id === highlightLocation.pointId) {
                                point.icon = 'img/highlightmarker.png';
                                vm.center = {
                                    latitude: point.coordinate.latitude,
                                    longitude: point.coordinate.longitude
                                }
                            }
                        });
				    });
                } else {
                    vm.highlightLocation = highlightLocation;
                    vm.center = {
                        latitude: highlightLocation.coordinate.latitude,
                        longitude: highlightLocation.coordinate.longitude
                    }
                }
			}
		});

        this.categories = mapService.categories.findAll();
        this.categoryToggled = function(category) {
            for (var i in vm.points) {
                // suboptimal, but i can't find solution to trigger the reload without changing the icon :(
                if (vm.points[i].category && vm.points[i].category.name === category.name) {
                    vm.points[i].markerOptions.visible = category.selected;
                    vm.points[i].icon = vm.points[i].orginalIcon.replace('img/', category.selected ? 'img/' : 'img/empty_');
                }
            }
        }
    }

	function MapService($resource, BASE_URL) {
        function getMarkerImage(point) {
            var markerPrefix= '';
            if (point.category) {
                if (point.category.cssClass) {
                    markerPrefix = point.category.cssClass + '-';
                }
            }

            return 'img/' + markerPrefix + 'marker.png';
        }

		return {
			locations: new $resource(BASE_URL + '/locations/:name', {}, {
                findByName: {method: 'GET', params: {name: '@name'}}
            }),

			points: new $resource(BASE_URL + '/points', {}, {
                findAll: {method: 'GET', isArray: true, transformResponse: function(rawData) {
                    var data = angular.fromJson(rawData);
                    for (var i in data) {
                        data[i].icon = getMarkerImage(data[i]);
                        data[i].orginalIcon = getMarkerImage(data[i]);
                        data[i].markerOptions = { visible: true };
                    }

                    return data;
                }}
            }),

            categories: new $resource(BASE_URL + '/mapcategories', {}, {
                findAll: {method: 'GET', isArray: true, transformResponse: function(rawData) {
                    var data = angular.fromJson(rawData);
                    for (var i in data) {
                        data[i].selected = true;
                    }
                    return data;
                }}
            })
		}
	}

    function UiMapAsyncLoaderCallback(uiGmapGoogleMapApiProvider) {
        uiGmapGoogleMapApiProvider.configure({
            v: '3.24',
            libraries: 'weather,geometry,visualization'
        });
    }

	angular.module('ehfgApp.map', ['uiGmapgoogle-maps'])
		.controller('MapCtrl', ['$scope', 'MapService', 'points', 'highlightLocation', MapCtrl])
		.factory('MapService', ['$resource', 'BASE_URL', MapService])
        .config(['uiGmapGoogleMapApiProvider', UiMapAsyncLoaderCallback])
})();