(function() {
    //var BASE_URL = 'http://192.168.0.14:8080/rest';
    //var BASE_URL = 'http://localhost:8080/rest';
	var BASE_URL = 'http://backend-ehfg.rhcloud.com/rest';

	angular.module('ehfgApp.config', [])
        .value('BASE_URL', BASE_URL)
}());