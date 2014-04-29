/**
 * creates a jquery mobile view, with the given parameters.
 * first, the list elements are added to the element with the root id.
 *
 * the jquery mobile element of the list
 * after that, the list view will be rendered by the refresh function of
 *
 * if the page and page field parameters are given, a link will be rendered in the
 * list elements
 *
 *
 * @param elementId (required) id of the html root list
 * @param source (required) array with the given elements
 * @param labelField (required) field in the objects of the array, of which the text should be rendered
 * @param page (optional) id of the page to be redirected
 * @param pageField (optional) field of the objects, were the parameter for the redirect will be rendered, e.g. 'id'
 */
var createListView = function(elementId, source, labelField, page, pageField) {
    var list = $('#' + elementId);

    $.each(list.children(), function(index, value) {
        if ($(value).data('dynamic-creation')) {
            $(value).remove();
        }
    });

    $.each(source, function(index, current) {
        var item = new String();
        item += '<li data-dynamic-creation="true">';
        if (page !== undefined) {
            item += "<a href=#";
            item += page;

            if (pageField !== undefined) {
                item += "?id=";
                item += current[pageField];
            }

            item += ">";
        }

        item += current[labelField !== undefined ? labelField : 'name'];

        if (page !== undefined) {
            item += "</a>";
        }

        item += "</li>";
        list.append(item);
    });

    list.listview('refresh');
};

var createSessionList = function(elementId, source) {
    var list = $('#' + elementId);
    list.children().remove();

    var item = '';
    $.each(source, function(dayIndex, currentDay) {
        item += '<li>' + currentDay.description + '</li>';

        $.each(currentDay.sessions, function(sessionIndex, currentSession) {
            item += '<li>';
            item += '<a href="#session-detail?id=' + currentSession.id + '">' + currentSession.name + '</a>';
            item += '</li>';
        });
    });

    list.append(item);
    list.listview('refresh');
}

/**
 * shorthand function for searching for a given id in the list
 * internally calls findByPropertyInList(list, 'id', value);
 *
 * if it should be searched for anything else than id, then call findByPropertyInList
 *
 * @param list source list
 * @param value to be searched for
 * @returns the value with the given id, or {null} otherwise
 */
var findByIdInList = function(list, value) {
    return findByPropertyInList(list, 'id', value);
};

/**
 * searches in the given list for the object with the given propertyValue as property
 * of the objects
 *
 * FIXME: can/should be refactored to a native for loop, if any performance problems occur
 * would also be better to be independent of jQuery, but who the hell does not use jQuery?
 *
 *
 * @param list source list
 * @param propertyName name of the property
 * @param propertyValue value of the property
 * @returns the value with the given propertyName, or {null} otherwise
 */
var findByPropertyInList = function(list, propertyName, propertyValue) {
    var result = null;
    $.each(list, function(index, value) {
        if (value[propertyName] == propertyValue) {
            result = value;
        }
    });

    return result;
};

/**
 * shorthand function for searching the values of the given list by the paramter 'id' of the objects
 * internally call findListByPropertyNameInList(list, 'id', value)
 *
 * FIXME: can most likely be removed, because ids normally should be unique
 *
 * @param list source for the search
 * @param value to be searched
 * @returns array with the given parameters
 */
var findListByIdInList = function(list, value) {
    return findListByPropertyNameInList(list, 'id', value);
};

/**
 * searches in the given list for the object with the given propertyValue as property
 * of the objects
 *
 * FIXME: see findByPropertyInList
 *
 * @param list source for the search
 * @param propertyName name of the property to be searched for. e.g. 'sessionId'
 * @param propertyValue value to be searched for
 * @returns an {Array} with the given values
 */
var findListByPropertyNameInList = function(list, propertyName, propertyValue) {
    var result = [];
    $.each(list, function(index, value) {
        if (value[propertyName] === propertyValue) {
            result.push(value);
        }
    });

    return result;
};

/**
 * calls the rest function with the given url extension, and calls the callbackFn when successful
 * internally calls restCallWithParams with an empty parameter object
 *
 * @param urlExtension last bit of the url to be added
 * @param callbackFn function to be called when the call was successful
 */
var restCall = function(urlExtension, callbackFn) {
    restCallWithParams(urlExtension, {}, callbackFn);
}

/**
 * calls the rest function with the given url extension, passing params to the server. after that, the callbackFn is
 * called when being successful
 *
 * @param urlExtension last bit of the url to be added
 * @param params to be sent to the server
 * @param callbackFn function to be called when the call was successful
 */
var restCallWithParams = function(urlExtension, params, callbackFn) {
    var url = SERVER_URL + '/rest/' + urlExtension;
    $.ajax(url, {
        headers: {'Access-Control-Allow-Origin': '*'},
        crossDomain: true,
        contentType: 'application/json',
        type: 'GET',
        dataType: 'jsonp',
        data: params
    }).success(function(data) {
        callbackFn(data);
    });
}

var addMarker = function(map, positionData) {
    var coordinate = positionData.coordinate;
    var position = new google.maps.LatLng(coordinate.xValue, coordinate.yValue);

    var marker = new google.maps.Marker({
        position: position,
        map: map
    });

    var createDialog = (function(data) {
        return function() {
            $('#pointHeader').text(data.name);
            $('#pointDescription').text(data.description);
            $('#pointAddress').text(data.address);
            $('#pointContact').text(data.contact);
            $('#pointWebsite').text(data.website);

            $('#map-dialog').css('display', '');
            $('#map-dialog').popup({theme: 'a'}).popup('open');
        };
    })(positionData);

    google.maps.event.addListener(marker, 'click', createDialog);
}
