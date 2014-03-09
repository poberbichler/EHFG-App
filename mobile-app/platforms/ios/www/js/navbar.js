$(function() {
    $("[data-role='navbar']").navbar();
    $("[data-role='footer']").toolbar({theme: "a"});
    //$("[data-role='header']").toolbar({theme: "a"});
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