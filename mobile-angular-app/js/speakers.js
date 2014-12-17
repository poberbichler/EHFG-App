(function() {
	var SpeakerCtrl = function(speakerService) {
		var vm = this;
		speakerService.findAll().then(function(data) {
			vm.speakers = data;
		});
		
		vm.filter = '';
	}
	
	var SpeakerDetailCtrl = function($stateParams, speakerService, sessionService) {
		var vm = this;
		
		speakerService.findById($stateParams.speakerId).then(function(data) {
			vm.speaker = data;
		});
		
		sessionService.findBySpeakerId($stateParams.speakerId).then(function(data) {
			vm.sessions = data;
		});
	}
	
	var SpeakerResource = function($resource, BASE_URL) {
		return $resource(BASE_URL + '/speaker/all?callback=JSON_CALLBACK', {}, {
			findAll: {method: 'JSONP', isArray:true}
		});
	}
	
	var SpeakerService = function($q, localStorageService, speakerResource) {
	    return {
	    	findAll: findAll,
	    	findById: findById,
	    	findByIds: findByIds
	    }
	    
	    function findAll() {
	    	var storage = localStorageService.findSpeakers();
            if (storage.length === 0) {
            	return speakerResource.findAll(function(data) {
            		localStorageService.setSpeakers(data);
            	}).$promise;
            }
            
            return $q.when(storage);
	    }
	    
	    function findById(speakerId) {
	    	return this.findAll().then(function(speakers) {
	    		for (var i in speakers) {
	    			if (speakers[i].id === speakerId) {
	    				return $q.when(speakers[i]);
	    			}
	    		}
	    		
	    		return null;
	    	});
	    }
	    
	    function findByIds(speakerIds) {
	    	return this.findAll().then(function(speakers) {
	    		var result = [];
	    		for (var i in speakers) {
	    			if (speakerIds.indexOf(speakers[i].id) !== -1) {
	    				result.push(speakers[i]);
	    			}
	    		}
	    		
	    		return $q.when(result);
	    	});
	    }
	}
	
	angular.module('ehfgApp.speakers', [])
		.controller('SpeakerCtrl', ['SpeakerService', SpeakerCtrl])
		.controller('SpeakerDetailCtrl', ['$stateParams', 'SpeakerService', 'SessionService', SpeakerDetailCtrl]) 
		.factory('SpeakerResource', ['$resource', 'BASE_URL', SpeakerResource])
		.factory('SpeakerService', ['$q', 'LocalStorageService', 'SpeakerResource', SpeakerService])
})()