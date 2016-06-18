(function() {
    function SearchCtrl($stateParams, searchResult) {
        console.log('searchParam: ', $stateParams.searchParam);
        console.log('data from the backend: ', searchResult);
        this.searchParam = $stateParams.searchParam;
        this.searchResult = searchResult;
    }

    function SearchResource($resource, BASE_URL) {
        return $resource(BASE_URL + '/search/:input', {input: '@input'});
    }

    angular.module('ehfgApp.search', [])
        .controller('SearchCtrl', SearchCtrl)
        .factory('SearchResource', ['$resource', 'BASE_URL', SearchResource])
})();