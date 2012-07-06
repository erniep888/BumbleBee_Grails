var bumblebeeFilterCookie = 'bumblebee_feature_filter1';

function saveFilter(filterValue){
    var filterCookieValue = $.cookie(bumblebeeFilterCookie);
    if (filterCookieValue != null){
        $.cookie(bumblebeeFilterCookie, null, { expires: 7, path: '/' });
    }

    $.cookie(bumblebeeFilterCookie, filterValue, { expires: 7, path: '/' });
}

function getFilterValue(){
    var filterValue = '';
    var filterCookieValue = $.cookie(bumblebeeFilterCookie);
    if (filterCookie != null)  {
        filterValue = filterCookieValue;
    }

    return filterValue;
}