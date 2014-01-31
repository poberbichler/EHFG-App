$(function() {
    $("[data-role='navbar']").navbar();
    $("[data-role='footer'], [data-role='header']").toolbar();
});

$(document).on("pageshow", "[data-role='page']", function() {
    $("[data-role='footer'], [data-role='header']").attr("data-theme", "a");

    var test = $("[data-role='navbar'] a.ui-btn-active");
    console.log(test);
    $("[data-role='navbar'] a").each(function(index, element) {

    });

    /*
    var current = $(this).jqmData("title");

    var asdf = $("[data-role='header'] h1");
    asdf.text("sessions");

    //console.log(asdf.text());
    //$("[data-role='header'] h1").text(current);



        console.log('this:', $(this).text(), 'asdf:', asdf.text());
        if ($(this).text() === asdf.text()) {
            console.log('adding...');
            $(this).addClass("ui-btn-active");

            console.log($(this));
        }
    }); */
});