(function() {
	var TwitterCtrl = function(twitterService) {
		this.tweetResource = twitterService.tweetData;
		this.loadMoreTweets = twitterService.loadMoreTweets;
		this.updateFeed = twitterService.updateFeed;

		twitterService.init();
	}
	
	var TwitterService = function(twitterResource) {
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
				});
			}
		}
	}
	
	var TwitterResource = function($resource, BASE_URL) {
		var pageResource = new $resource(BASE_URL + '/twitter/tweetpage/:page?callback=JSON_CALLBACK', {page: 0}, {
			findInitial: {method: 'JSONP', isArray: false},
			findMore: {method: 'JSONP', isArray: false, params: {page: '@page'}}
		});
		
		var updateResource = new $resource(BASE_URL + '/twitter/update/:lastTweet?callback=JSON_CALLBACK', {lastTweet: '@lastTweet'}, {
			findNewer: {method: 'JSONP', isArray: true}
		});

		return {
			findMore: pageResource.findMore,
			findNewer: updateResource.findNewer,
			findInitial: pageResource.findInitial
		}
	}
	
	angular.module('ehfgApp.twitter', [])
		.controller('TwitterCtrl', ['TwitterService', TwitterCtrl])
		.factory('TwitterResource', ['$resource', 'BASE_URL', TwitterResource])
		.factory('TwitterService', ['TwitterResource', TwitterService])
})();
