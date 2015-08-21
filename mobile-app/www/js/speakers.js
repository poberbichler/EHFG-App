(function() {
    function SpeakerCtrl(speakerService) {
        this.filter = '';
        this.speakers = speakerService.findAll();
    }

	function SpeakerDetailCtrl($stateParams, speakerService, sessionService) {
        this.speaker = speakerService.findById($stateParams.speakerId);
        this.sessions = sessionService.findBySpeakerId($stateParams.speakerId);
	}
	
	function SpeakerResource($resource, BASE_URL) {
		return $resource(BASE_URL + '/speakers', {}, {
			findAll: {method: 'GET', isArray:true}
		});
	}
	
	function SpeakerService(cacheFactory, speakerResource) {
        var speakerCache = cacheFactory.get('speakers');

        if (!speakerCache) {
            speakerCache = new cacheFactory('speakers', {
                onExpire: initCache
            });

            if (speakerCache.keys().length === 0) {
                initCache('all');
            }
        }

        function initCache(key) {
            if (key === 'all') {
                speakerCache.put('all', speakerResource.findAll(function(data) {
                    for (var i in data) {
                        var current = data[i];
                        if (current.id !== undefined) {
                            speakerCache.put(current.id, current);
                        }
                    }
                }).$promise);
            }
        }

	    return {
            findAll: findAll,
            findById: findById,
            findByIds: findByIds
        };

	    function findAll() {
            return speakerCache.get('all');
	    }
	    
	    function findById(speakerId) {
            return speakerCache.get(speakerId);
	    }
	    
	    function findByIds(speakerIds) {
            var result = [];
            for (var i in speakerIds) {
                result.push(speakerCache.get(speakerIds[i]));
            }

            return result;
        }
	}

    function ImageLookup() {
        return function(input) {
            return input;
        }
    }

	angular.module('ehfgApp.speakers', [])
		.controller('SpeakerCtrl', ['SpeakerService', SpeakerCtrl])
		.controller('SpeakerDetailCtrl', ['$stateParams', 'SpeakerService', 'SessionService', SpeakerDetailCtrl])
		.factory('SpeakerResource', ['$resource', 'BASE_URL', SpeakerResource])
		.factory('SpeakerService', ['CacheFactory', 'SpeakerResource', SpeakerService])
        .filter('imageLookup', [ImageLookup])
})()