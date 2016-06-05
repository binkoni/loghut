<form id="search-form" class="form-group" class="" action="${settings.getSetting('admin.url')}/search.do" method="get">
    <input type="hidden" name="action" value="search"/>
    <input type="hidden" name="page_unit" value="10"/>
    <input type="hidden" name="page" value="1"/>
    <div class="row">
        <div class="col-md-2"> 
            <select id="search-filter-select" class="form-control" form="search-form">
                <option value="title">Title</option>
                <option value="tag_names">TagNames</option>
                <option value="text">Text</option>
                <option value="years">Years</option>
                <option value="months">Months</option>
                <option value="days">Days</option>
            </select>
            <input id="search_filter" type="hidden" name="search_filter" value="title"/>
            <script>
                document.getElementById("search-filter-select").addEventListener("change", function(event) {
                    document
                    .getElementById("search_filter")
                    .setAttribute("value", event.target.options[event.target.selectedIndex].value); 
                });
            </script>
        </div>
        <div class="col-md-5">
            <input id="search-keyword" class="col-md-6 form-control" type="text" name="search_keyword"/>
        </div>
        <div class="col-md-5">
            <input class="btn btn-default" type="submit" value="Search"/>
        </div>
    </div>
</form>
<hr/>