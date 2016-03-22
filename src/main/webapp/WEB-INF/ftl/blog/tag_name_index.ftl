<!DOCTYPE html>
<html>
    <head>
        <title>Tag Names - Gon's Blog - gonapps.io</title>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,user-scalable=yes"/>
        <link href="http://gonapps.io/res/favicon.gif" rel="shortcut icon" type="image/x-icon"/>
        <script src="//code.jquery.com/jquery.min.js" type="text/javascript"></script>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
        <style>*{word-wrap:break-word;max-width:100%}</style>
    </head>
    <body>
        <div class="container">
            <header class="page-header text-center">
                <h1>
                    <a href="${settings.getSetting('blog.url')}">Gon's Blog</a>
                </h1>
            </header>
            <main>
                <div class="list-group">
                    <div class="list-group-item">
                        <a href="..">
                        ..
                        </a>
                    </div>
                    <#list tagNames as tagName>
                    <div class="list-group-item">
                        <a href="${settings.getSetting('tags.url')}/${tagName}">
                        ${tagName}
                        </a>
                    </div>
                    </#list>
                </div>
            </main>
            <footer class="panel panel-default">
                <ul class="nav nav-pills">
                    <li>
                        <a href="${settings.getSetting('admin.url')}">
                        <span class="glyphicon glyphicon-user"></span> Admin
                        </a>
                    </li>
                    <li>
                        <a href="${settings.getSetting('posts.url')}">
                        <span class="glyphicon glyphicon-folder-open"></span> Archive
                        </a>
                    </li>
                    <li>
                        <a href="${settings.getSetting('tags.url')}">
                        <span class="glyphicon glyphicon-tags"></span> Tags
                        </a>
                    </li>
                </ul>
                <ul class="nav nav-pills">
                    <li>
                        <a href="${settings.getSetting('blog.url')}">
                        <span class="glyphicon glyphicon-home"></span> Home
                        </a>
                    </li>
                    <li>
                        <a href="http://gonapps.io/apps">
                        <span class="glyphicon glyphicon-th"></span> Gon Apps
                        </a>
                    </li>
                    <li>
                        <a href="https://kiwiirc.com/client/irc.freenode.net/#gonapps">
                        <span class="glyphicon glyphicon-volume-up"></span> IRC Chat
                        </a>
                    </li>
                    <li>
                        <a href="http://gonapps.io/about.html">
                        <span class="glyphicon glyphicon-info-sign"></span> About
                        </a>
                    </li>
                </ul>
            </footer>
        </div>
    </body>
</html>