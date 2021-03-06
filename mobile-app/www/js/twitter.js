(function() {
	function TwitterCtrl(twitterService) {
		this.tweetResource = twitterService.tweetData;
		this.loadMoreTweets = twitterService.loadMoreTweets;
		this.updateFeed = twitterService.updateFeed;
        this.toggleShowAllTweets = twitterService.toggleShowAllTweets;

		twitterService.init();

        this.showAllTweets = twitterService.showAllTweets;
        this.showTweet = function(tweet) {
            return twitterService.showAllTweets.value ? true : tweet.retweet === false;
        }
	}

	function TwitterService($rootScope, $ionicLoading, $ionicPopup, cacheFactory, twitterResource) {
		var tweetData = {};
        var twitterCache = cacheFactory.get('twitter');
        if (!twitterCache) {
            twitterCache = new cacheFactory('twitter', {
                deleteOnExpire: 'none'
            });
        }

        // we have to use an object in this case
        var showAllTweets = {
            value: true
        };

        var showTweetsCacheValue = twitterCache.get('showAllTweets');
        if (showTweetsCacheValue === undefined) {
            twitterCache.put('showAllTweets', false);
            showTweetsCacheValue = false;
        }

        showAllTweets.value = showTweetsCacheValue;

		function mapData(data) {
			tweetData.morePages = data.morePages;
			tweetData.currentPage = data.currentPage;
			tweetData.currentHashtag = data.currentHashtag;
		}

		return {
            init: init,
            tweetData: tweetData,
            updateFeed: updateFeed,
            loadMoreTweets: loadMoreTweets,
            showAllTweets: showAllTweets,
            toggleShowAllTweets: toggleShowAllTweets
        }

		function init() {
			twitterResource.findInitial(function(result) {
				mapData(result);
				tweetData.tweets = result.data;
			});
		}

        function toggleShowAllTweets(newValue) {
            twitterCache.put('showAllTweets', newValue)
        }

		function loadMoreTweets() {
            $ionicLoading.show({
                template: '<ion-spinner></ion-spinner><br />Loading...'
            });
			twitterResource.findMore({page: tweetData.currentPage+1}, function(result) {
				mapData(result);
				tweetData.tweets = tweetData.tweets.concat(result.data)
			}, function() {
                $ionicPopup.alert({
                    title: 'Something went wrong...',
                    template: 'Connection to the server could not be established'
                });
            }).$promise.finally(function() {
               $ionicLoading.hide();
            });
		}
		
		function updateFeed() {
			if (tweetData !== null && tweetData.tweets[0] !== null) {
				var latestTweet = tweetData.tweets[0];
				twitterResource.findNewer({'lastTweet': latestTweet.timestamp}, function(result) {
					tweetData.tweets = result.concat(tweetData.tweets);
				}, function() {
                    $ionicPopup.alert({
                        title: 'Something went wrong...',
                        template: 'Connection to the server could not be established'
                    });
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

	function TrustedHtmlFilter($sce) {
		return function(input) {
			return $sce.trustAsHtml(input);
		}
	}

    function TweetDirective() {
        return {
            scope: {
                tweet: '=',
                showAllTweets: '='
            },
            restrict: 'E',
            templateUrl: 'templates/tweet.html'
        }
    }
	
	angular.module('ehfgApp.twitter', [])
		.controller('TwitterCtrl', ['TwitterService', TwitterCtrl])
		.factory('TwitterResource', ['$resource', 'BASE_URL', TwitterResource])
		.factory('TwitterService', ['$rootScope', '$ionicLoading', '$ionicPopup', 'CacheFactory', 'TwitterResource', TwitterService])
		.filter('twitterDateFilter', ['$filter', 'UtcTimeService', TwitterDateFilter])
		.filter('trustedHtml', ['$sce', TrustedHtmlFilter])
        .directive('tweet', [TweetDirective])
})();
