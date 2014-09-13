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

    // if the source is empty, do not iterate over the elements
    if (source != null && source != undefined && source.length != 0) {
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

        list.show();
        list.listview('refresh');
    }

    // and also hide the list
    else {
        list.hide();
    }
};

var createSessionList = function(elementId, source) {
    var list = $('#' + elementId);
    list.children().remove();

    var favouriteSessionSelected = isFavouriteSessionSelected();
    var favouriteSessions = getFavouriteSessions();
    var item = appendCurrentSessions(source);

    $.each(source, function(dayIndex, currentDay) {

        var sessionItem = '';
        $.each(currentDay.sessions, function(sessionIndex, currentSession) {
            // favourite session selected, and list contains current session
            if (favouriteSessionSelected && favouriteSessions.indexOf(currentSession.id) !== -1) {
                sessionItem += '<li>';
                sessionItem += '<a href="#session-detail?id=' + currentSession.id + '">' + currentSession.code + ' - ' + currentSession.name + '</a>';
                sessionItem += '</li>';
            }

            // add every session
            else if (!favouriteSessionSelected) {
                sessionItem += '<li>';
                sessionItem += '<a href="#session-detail?id=' + currentSession.id + '">' + currentSession.code + ' - ' + currentSession.name + '</a>';
                sessionItem += '</li>';
            }
        });


        if (sessionItem.length !== 0) {
            item += '<li>' + currentDay.description + ' - ' + new Date(currentDay.timestamp).toSessionDate() + '</li>';
            item += sessionItem;
        }
    });

    list.append(item);
    list.listview('refresh');
}

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
        map: map,
        icon: 'img/marker2.png'
    });

    var createDialog = (function(data) {
        return function() {
            $('#pointHeader').text(data.name);
            $('#pointDescription').html(data.description);
            $('#pointAddress').text(data.address);


            var contact = $('#pointContact');
            contact.text(data.contact);
            if (data.contact === null || data.contact.length === 0) {
                contact.parent().hide();
            }
            
            var website = $('#pointWebsite');
            website.text(data.website);
            if (data.website === null || data.contact.length === 0) {
                website.parent().hide();
            }

            $('#map-dialog').css('display', '');
            $('#map-dialog').popup({theme: 'a'}).popup('open');
        };
    })(positionData);

    google.maps.event.addListener(marker, 'click', createDialog);
}

var appendCurrentSessions = function(sessionList) {
    var currentSessions = [];
    var currentTimestamp = new Date().getTime();
    var item = '';

    for (var i in sessionList) {
        var sessions = sessionList[i].sessions;
        for (var j in sessions) {
            var session = sessions[j];
            if (session.start < currentTimestamp && currentTimestamp < session.end) {
                currentSessions.push(session);
            }
        }
    }

    if (currentSessions.length != 0) {
        item += "<li>What's going on right now?</li>";
        for (var i in currentSessions) {
            var session = currentSessions[i];
            item += '<li>';
            item += '<a href="#session-detail?id="' + session.id + '">' + session.name + '</a>';
            item += '</li>';
        }
    }

    return item;
}


Date.prototype.toSessionTime = function() {
    var hours = this.getHours();
    var minutes = this.getMinutes();

    var result = '';

    if (hours < 10) {
        result += '0';
    }

    result += hours + ':';

    if (minutes < 10) {
        result += '0';
    }

    result += minutes;

    return result;
}

Date.prototype.toSessionDate = function() {
    var day = this.getDate();
    var month = this.getMonth();

    var daySuffix = '';
    var lastNumberOfDay = day % 10;

    switch (lastNumberOfDay) {
    case 1:
        daySuffix = 'st';
        break;
    case 2:
        daySuffix = 'nd';
        break;
    case 3:
        daySuffix = 'rd';
        break;
    default:
        daySuffix = 'th';
    }

    var englishMonth = '';
    switch (month) {
        case 0:
            englishMonth = 'January';
            break;
        case 1:
            englishMonth = 'February';
            break;
        case 2:
            englishMonth = 'March';
            break;
        case 3:
            englishMonth = 'April';
            break;
        case 4:
            englishMonth = 'May';
            break;
        case 5:
            englishMonth = 'June';
            break;
        case 6:
            englishMonth = 'July';
            break;
        case 7:
            englishMonth = 'August';
            break;
        case 8:
            englishMonth = 'September';
            break;
        case 9:
            englishMonth = 'October';
            break;
        case 10:
            englishMonth = 'November';
            break;
        case 11:
            englishMonth = 'December';
            break;
    }

    return day + daySuffix + ' ' + englishMonth;
}
