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

    function SpeakerImage() {
        return {
            restrict: 'A',
            link: linkFunction,
            scope: {imageUrl: '&speakerImage'}
        }

        function linkFunction(scope, element, attrs) {
            var imageId = scope.imageUrl().split('/').pop();

            var firstUrl = 'img/speakers/'.concat(imageId);
            var secondUrl = scope.imageUrl();
            var thirdUrl = 'img/speakers/speakersdefaultperson.jpg';

            attrs.$set('src', firstUrl);
            element.bind('error', function(event) {
                if (attrs.src === firstUrl) {
                    attrs.$set('src', secondUrl);
                } else if (attrs.src === secondUrl) {
                    attrs.$set('src', thirdUrl);
                }
            });

        }
    }

	angular.module('ehfgApp.speakers', [])
		.controller('SpeakerCtrl', ['SpeakerService', SpeakerCtrl])
		.controller('SpeakerDetailCtrl', ['$stateParams', 'SpeakerService', 'SessionService', SpeakerDetailCtrl])
		.factory('SpeakerResource', ['$resource', 'BASE_URL', SpeakerResource])
		.factory('SpeakerService', ['CacheFactory', 'SpeakerResource', SpeakerService])
        .directive('speakerImage', [SpeakerImage])
})()