
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
        <form action="${settings.getSetting('admin.url')}/search.do" method="get">
            <input type="hidden" name="action" value="search"/>
            <label for="title">Title</label>
            <input type="text" name="title"/>
            <div style="display:none">
                <label for="tags">Tags</label>
                <input type="text" name="tags"/>
                <label for="years">Years</label>
                <input type="text" name="years"/>
                <label for="months">Months</label>
                <input type="text" name="months"/>
                <label for="days">Days</label>
                <input type="text" name="days"/>
            </div>
            <input type="hidden" name="page_unit" value="10"/>
            <input type="hidden" name="page" value="1"/>
            <input type="submit" value="Search"/>
        </form>
        <hr/>