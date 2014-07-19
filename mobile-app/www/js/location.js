var LOCATION = "location";

var locationService = function() {
    return {
        findCoordinatesByName: function(locationName, callback) {
            checkForItemByProperty(LOCATION, callback, locationName, 'name');
        }
    }
};