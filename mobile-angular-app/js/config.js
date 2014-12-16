(function() {
	//var BASE_URL = 'http://192.168.0.11:8080/rest';
	var BASE_URL = 'http://localhost:8080/rest';
	//var BASE_URL = 'http://app-ehfg.rhcloud.com/app-web/rest';
	
	angular.module('ehfgApp.config', []).value('BASE_URL', BASE_URL);
}());