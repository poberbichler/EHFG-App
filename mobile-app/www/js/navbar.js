$(function() {
    var theme = 'a';
    //var theme = 'b';

    var settingsButton = '<a href="#settingsPanel" id="settingsButton" ';
    settingsButton += 'class="ui-btn-right ui-btn ui-btn-inline ui-mini ui-corner-all ui-btn-icon-right ui-icon-gear" ';
    settingsButton += '>&nbsp;</a>';

    $("[data-role='navbar']").navbar();
    $("[data-role='footer']").toolbar({theme: theme});
    $("[data-role='header']").toolbar({theme: theme}).append(settingsButton);

    $("[data-settings='off'] > #settingsButton").remove();

    $('#settingsPanel').panel();
    $('#panelList').listview();
});

var CLICK_ACTION = 'click';

$('#showFavouriteSessions').on(CLICK_ACTION, function() {
    var element = $(this);
    $('li[class=selected]').removeClass('selected');

    element.parent().addClass('selected');
});

$('#showAllSessions').on(CLICK_ACTION, function() {
    var element = $(this);
    $('li[class=selected]').removeClass('selected');

    element.parent().addClass('selected');
});

$('#resetData').on(CLICK_ACTION, function() {
    localStorage.clear();
    window.location.reload();
});

$('#aboutLink').on(CLICK_ACTION, function() {
    $('#aboutPopup').css('display', '');
    $('#aboutPopup').popup({theme: 'a'}).popup('open');
});

$(document).on("pageshow", "[data-role='page']", function() {
    // cache some further needed variables
    var activeClass = "ui-btn-active";
    var header = $(this).find("[data-role='header']");
    var overrideHeader = $.mobile.activePage.data('override-header');

    // remove acitve class first, in case of a page refresh
    $("[data-role='navbar'] a." + activeClass).removeClass(activeClass);

    // iterate over each element in the navbar
    $("[data-role='navbar'] a").each(function() {
        var currentElement = $(this);

        if (overrideHeader !== undefined) {
            if (currentElement.text().trim().toLowerCase() === overrideHeader) {
                currentElement.addClass(activeClass);
            }
        }

        else {
            if (header !== undefined && header.text() !== undefined && currentElement.text() !== undefined) {
                if (header.text().trim().toLowerCase() === currentElement.text().trim().toLowerCase()) {
                    currentElement.addClass(activeClass);
                }
            }
        }
    });
});

