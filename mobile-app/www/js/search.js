(function() {
    function SearchCtrl($stateParams, searchResult) {
        this.searchParam = $stateParams.searchParam;
        this.searchResult = searchResult;
    }

    function SearchResource($resource, BASE_URL) {
        return $resource(BASE_URL + '/search/:input', {input: '@input'});
    }

    function CapitalizeFilter($filter) {
        return function(input) {
            return input.charAt(0).toUpperCase() + $filter('lowercase')(input.slice(1));
        }
    }

    angular.module('ehfgApp.search', [])
        .controller('SearchCtrl', ['$stateParams', 'searchResult', SearchCtrl])
        .factory('SearchResource', ['$resource', 'BASE_URL', SearchResource])
        .filter('capitalize', ['$filter', CapitalizeFilter])
})();