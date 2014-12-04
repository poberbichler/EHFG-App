(function() {
	var TwitterCtrl = function(twitterService) {
		this.tweetResource = twitterService.tweetData;
	    this.loadMoreTweets = twitterService.loadMoreTweets;
	    
	    twitterService.init();
	}
	
	var TwitterService = function(twitterResource) {
		var tweetData = {};
		
		return {
			init: init,
			tweetData: tweetData,
			loadMoreTweets: loadMoreTweets
		} 

		function mapData(data) {
			tweetData.morePages = data.morePages;
			tweetData.currentPage = data.currentPage;
		}
		
		function init() {
			twitterResource.findInitial(function(data) {
				mapData(data);
				tweetData.tweets = data.tweets;
			});
		}
		
		function loadMoreTweets() {
			twitterResource.findMore({page: tweetData.currentPage+1}, function(data) {
				mapData(data);
				tweetData.tweets = tweetData.tweets.concat(data.tweets)
			});
		}
	}
	
	var TwitterResource = function($resource) {
		return new $resource(BASE_URL + '/twitter/tweetpage/:page?callback=JSON_CALLBACK', {page: 0}, {
			findInitial: {method: 'JSONP', isArray: false},
			findMore: {method: 'JSONP', isArray: false, params: {page: '@page'}}
		});
	}
	
	angular.module('ehfgApp.twitter', [])
		.controller('TwitterCtrl', ['TwitterService', TwitterCtrl])
		.factory('TwitterResource', ['$resource', TwitterResource])
		.factory('TwitterService', ['TwitterResource', TwitterService])
})();
