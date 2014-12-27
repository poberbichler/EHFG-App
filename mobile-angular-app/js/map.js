(function() {
	function MapCtrl(mapService, highlightLocation) {
		this.zoom = 16;
		this.points = mapService.points.findAll();
		
		if (highlightLocation === null) {
			this.center = {latitude: 47.170329, longitude: 13.103852}
		}
		
		else {
			this.highlightLocation = highlightLocation;
			// copy the properties, otherwise the center location of 'hightlightLocation' will be changed
			this.center = {
					latitude: highlightLocation.coordinate.latitude,
					longitude: highlightLocation.coordinate.longitude
			}
		}
	}
	
	function MapService($resource, BASE_URL) {
		return {
			locations: new $resource(BASE_URL + '/location/:method/:name?callback=JSON_CALLBACK', {}, {
					findAll: {method: 'JSONP', isArray: true, params: {method: 'all'}},
					findByName: {method: 'JSONP', params: {method: 'name', name: '@name'}}
				}),
			
			points: new $resource(BASE_URL + '/points/all?callback=JSON_CALLBACK', {}, {
					findAll: {method: 'JSONP', isArray: true, transformResponse: function(data) {
						for (var i in data) {
							data[i].icon = 'img/marker.png';
						}
						return data;
					}}
				})
		}
	}
	
	angular.module('ehfgApp.map', ['uiGmapgoogle-maps'])
		.controller('MapCtrl', ['MapService', 'highlightLocation', MapCtrl])
		.factory('MapService', ['$resource', 'BASE_URL', MapService])
})();

function onGoogleReady() {
	angular.bootstrap(document.getElementById("map"), ['uiGmapgoogle-maps']);
}