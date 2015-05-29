(function() {
	function TwitterCtrl(twitterService) {
		this.tweetResource = twitterService.tweetData;
		this.loadMoreTweets = twitterService.loadMoreTweets;
		this.updateFeed = twitterService.updateFeed;

		twitterService.init();
	}
	
	function TwitterService($rootScope, twitterResource) {
		var tweetData = {};
		
		function mapData(data) {
			tweetData.morePages = data.morePages;
			tweetData.currentPage = data.currentPage;
			tweetData.currentHashtag = data.currentHashtag;
		}

		return {
			init: init,
			tweetData: tweetData,
			updateFeed: updateFeed,
			loadMoreTweets: loadMoreTweets
		} 

		function init() {
			twitterResource.findInitial(function(result) {
				mapData(result);
				tweetData.tweets = result.data;
			});
		}
		
		function loadMoreTweets() {
			twitterResource.findMore({page: tweetData.currentPage+1}, function(result) {
				mapData(result);
				tweetData.tweets = tweetData.tweets.concat(result.data)
			});
		}
		
		function updateFeed() {
			if (tweetData !== null && tweetData.tweets[0] !== null) {
				var latestTweet = tweetData.tweets[0];
				twitterResource.findNewer({'lastTweet': latestTweet.timestamp}, function(result) {
					tweetData.tweets = result.concat(tweetData.tweets);
				}).$promise.finally(function() {
                    $rootScope.$broadcast('scroll.refreshComplete');
                });
			}
		}
	}
	
	function TwitterResource($resource, BASE_URL) {
		var pageResource = new $resource(BASE_URL + '/twitter/page/:page', {page: 0}, {
			findInitial: {method: 'GET', isArray: false},
			findMore: {method: 'GET', isArray: false, params: {page: '@page'}}
		});
		
		var updateResource = new $resource(BASE_URL + '/twitter/update/:lastTweet', {lastTweet: '@lastTweet'}, {
			findNewer: {method: 'GET', isArray: true}
		});

		return {
			findMore: pageResource.findMore,
			findNewer: updateResource.findNewer,
			findInitial: pageResource.findInitial
		}
	}
	
	function TwitterDateFilter($filter, utcTimeService) {
		return function(input) {
			var second = 1000;
		    var minute = 1000 * 60;
		    var hour = minute * 60;
		    var day = hour * 24;
		
		    var difference = utcTimeService.getCurrentTime().getTime() - utcTimeService.getUtcTimeFor(input).getTime();
		    if (difference < day) {
		        if (difference < minute) {
		            var value = (difference/second).toFixed(0);
		            return "" + value + "s";
		        }

		        if (difference < hour) {
		            var value = (difference/minute).toFixed(0);
		            return "" + value + "m";

		        }

		        var value = (difference/hour).toFixed(0);
		        return "" + value + "h";
		    }
			
			return $filter('date')(input, 'MMM d, HH:mm', 'UTC');
		}
	}
	
	angular.module('ehfgApp.twitter', [])
		.controller('TwitterCtrl', ['TwitterService', TwitterCtrl])
		.factory('TwitterResource', ['$resource', 'BASE_URL', TwitterResource])
		.factory('TwitterService', ['$rootScope', 'TwitterResource', TwitterService])
		.filter('twitterDateFilter', ['$filter', 'UtcTimeService', TwitterDateFilter])
})();
