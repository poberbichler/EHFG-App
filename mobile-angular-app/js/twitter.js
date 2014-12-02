angular.module('ehfgApp.twitter', [])

.controller('TwitterCtrl', ['$scope', 'TwitterService', function($scope, twitterService) {
    twitterService.find(function(data) {
    	$scope.tweetResource = data;
    });
    
    $scope.loadMoreTweets = function() {
    	twitterService.findMore({page: $scope.tweetResource.currentPage+1}, function(data) {
    		var oldTweets = $scope.tweetResource.tweets;
    		
    		$scope.tweetResource = data;
    		$scope.tweetResource.tweets = oldTweets.concat(data.tweets);
    	});
    }
}])

.factory('TwitterService', ['$resource', function($resource) {
	return new $resource("http://localhost:8888/rest/twitter/tweetpage/:page?callback=JSON_CALLBACK", {page: 0}, {
		find: {method: 'JSONP', isArray: false},
		findMore: {method: 'JSONP', isArray: false, params: {page: '@page'}}
	})
}])
