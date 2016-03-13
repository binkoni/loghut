
<html lang="en-US">
    <head>
        <title>Admin Page</title>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,user-scalable=yes"/>
    </head>
    <body>
        <div>
            <a href="http://github.com/gonapps/loghut" style="background-color:lightgrey;display:block">
                <img src="http://i.imgur.com/jJH0lza.png" style="display:inline-block;vertical-align:middle;width:3rem;height:3rem"/>
                Powered by LogHut
            </a>
            <a href="${settings.getSetting('admin.url')}" style="font-size:2rem">Admin Page</a>
        </div>
        <a href="${settings.getSetting('blog.url')}">Blog Home</a>
        <a href="${settings.getSetting('admin.url')}/creation_form.do">New Post</a>
        <a href="${settings.getSetting('admin.url')}/backup.do">Download Backup</a>
        <a href="${settings.getSetting('admin.url')}/refresh_all.do">Refresh All Posts</a>
        <a href="${settings.getSetting('admin.url')}/logout.do">Logout</a>
        <hr>
        <script type="text/javascript">
            document.addEventListener("change", function(event) {
                if(event.target.id = "search_filter_select") {
                    document
                    .getElementById("search_filter")
                    .setAttribute("name",
                        event.target.options[event.target.selectedIndex].value); 
                }
            });
        </script>
        <form id="search_form" action="${settings.getSetting('admin.url')}/search.do" method="get">
            <input type="hidden" name="action" value="search"/>
            <select id="search_filter_select" form="search_form">
                <option value="title">Title</option>
                <option value="tag_names">TagNames</option>
                <option value="years">Years</option>
                <option value="months">Months</option>
                <option value="months">Days</option>
            </select>
            
            <input type="text" id="search_filter" name="title"/>
            
            <input type="hidden" name="page_unit" value="10"/>
            <input type="hidden" name="page" value="1"/>
            <input type="submit" value="Search"/>
        </form>
        <hr/>