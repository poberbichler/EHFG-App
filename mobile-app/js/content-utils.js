/**
 *
 * @param elementId
 * @param source
 * @param labelField
 * @param page
 * @param pageField
 */
var createListView = function(elementId, source, labelField, page, pageField) {
    var list = $('#' + elementId);
    list.children('li').remove();

    $.each(source, function(index, current) {
        var item = new String();
        item += "<li>";
        if (page !== undefined) {
            item += "<a href=#";
            item += page;

            if (pageField !== undefined) {
                item += "?id=";
                item += current[pageField];
            }

            item += ">";
        }

        item += current[labelField !== undefined ? labelField: 'name'];

        if (page !== undefined) {
            item += "</a>";
        }

        item += "</li>";
        list.append(item);
    });

    list.listview('refresh');
}