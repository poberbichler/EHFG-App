var router = new $.mobile.Router(
    // routes
    [{
        "#speaker-detail": {events: 'bC', handler: 'defaultHandler'},
        "#session-detail": {events: 'bC', handler: 'defaultHandler'},
        "#map": {events: 'bC', handler: 'defaultHandler'}
    }],

    // handler
    {
        defaultHandler: function(type, match, ui) {
            $.mobile.pageParameters = router.getParams(match[0]);
        }
    },

    // options
    {
        ajaxApp: true,
        defaultArgsRe:true
    }
);