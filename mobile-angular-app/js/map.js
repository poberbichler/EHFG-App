(function() {
	function MapCtrl($stateParams, mapService) {
		if ($stateParams.location !== undefined && $stateParams.location.length !== 0) {
			var highlightLocation = {};
			this.highlightLocation = highlightLocation;
		}

		
		this.zoom = 16;
		this.center = {
				latitude: 47.170329, 
				longitude: 13.103852
		}
		
		this.points = mapService.points.findAll();
		this.locations = mapService.locations.findAll();
	}
	
	function MapService($resource) {
		return {
			locations: new $resource(BASE_URL + '/location/all?callback=JSON_CALLBACK', {}, {
					findAll: {method: 'JSONP', isArray: true}
				}),
			
			points: new $resource(BASE_URL + '/points/all?callback=JSON_CALLBACK', {}, {
					findAll: {method: 'JSONP', isArray: true}
				})
		}
	}
	
	angular.module('ehfgApp.map', ['uiGmapgoogle-maps'])
		.controller('MapCtrl', ['$stateParams', 'MapService', MapCtrl])
		.factory('MapService', ['$resource', MapService])
})();

function onGoogleReady() {
	angular.bootstrap(document.getElementById("map"), ['uiGmapgoogle-maps']);
}