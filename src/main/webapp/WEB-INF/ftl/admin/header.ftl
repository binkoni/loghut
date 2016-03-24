<html lang="en-US">
    <head>
        <title>Admin Page</title>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,user-scalable=yes"/>
        <script src="//code.jquery.com/jquery.min.js" type="text/javascript"></script>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
        <style>*{word-wrap:break-word;max-width:100%}</style>
    </head>
    <body class="container panel">
        <div>
            <a href="http://github.com/gonapps/loghut" style="background-color:lightgrey;display:block">
                <img src="http://i.imgur.com/jJH0lza.png" style="display:inline-block;vertical-align:middle;width:3rem;height:3rem"/>
                Powered by LogHut
            </a>
            <h1>
                <a href="${settings.getSetting('admin.url')}">Admin Page</a>
            </h1>
        </div>
        <marquee id="loghut-ad-text"></marquee>
        <script>
        (function() {
            try {
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function() {
                    if(xhr.readyState == XMLHttpRequest.DONE) {
                        ad = JSON.parse(xhr.responseText);
                        var adText = document.getElementById("loghut-ad-text");
                        adText.innerHTML = "<a href=\"" + ad.link + "\">" + ad.text + "</a>";
                    }
                };
                xhr.open("GET", "http://gonapps.io/ad/loghut.ad", true);
                xhr.send();
            } catch(err) {}
        })();
        </script>
        <ul class="nav nav-pills">
        <li><a href="${settings.getSetting('blog.url')}">Blog Home</a></li>
        <li><a href="${settings.getSetting('admin.url')}/creation_form.do">New Post</a></li>
        <li><a href="${settings.getSetting('admin.url')}/backup.do">Download Backup</a></li>
        <li><a href="${settings.getSetting('admin.url')}/refresh_all.do">Refresh All Posts</a></li>
        <li><a href="${settings.getSetting('admin.url')}/logout.do">Logout</a></li>
        </ul>
        <hr>
        