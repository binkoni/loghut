<!DOCTYPE html>
<html>
    <head>
        <title>${post.title} - Gon's Blog - gonapps.io</title>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,user-scalable=yes"/>
        <meta name="keywords" content="<#list post.tags as tag>${tag.name}<#sep>,</#sep></#list>"/>
        <link href="http://gonapps.io/res/favicon.gif" rel="shortcut icon" type="image/x-icon"/>
        <script src="//code.jquery.com/jquery.min.js" type="text/javascript"></script>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="container">
            <script>
                document.addEventListener("click", function(event) {
                    if(event.target.innerHTML == "delete" && confirm("Do you really want to delete this post?") == false) {
                        event.preventDefault();
                    }
                });
            </script>
            <header class="page-header text-center">
                <h1>
                    <a href="http://gonapps.io/blog">Gon's Blog</a>
                </h1>
            </header>
            <main class="panel panel-default">
                <div id="loghut-post-title" class="panel-heading text-center">
                    <h4>${post.title}</h4>
                </div>
                <article id="loghut-post-text" class="panel-body">
                    ${post.text}
                </article>
                <div class="panel-footer">
                    <span>
                        <strong>Tags:(</strong>
                        <#list post.tags as tag>
                            <a class="loghut-post-tag label label-default" href="${settings.getSetting('tag.url')}${tag.path}">
                                ${tag.name}
                            </a>
                            &nbsp;
                        </#list>
                    </span>
                    <strong>)</strong>
                    <div>
                        <a class="btn btn-default" href="${settings.getSetting('admin.url')}/creation_form.do">
                        <span class="glyphicon glyphicon-pencil"></span> create
                        </a>
                        <a class="btn btn-default" href="${settings.getSetting('admin.url')}${post.modificationFormPath}">
                        <span class="glyphicon glyphicon-erase"></span> modify
                        </a>
                        <a class="btn btn-default" href="${settings.getSetting('admin.url')}${post.deletePath}">
                        <span class="glyphicon glyphicon-trash"></span> delete
                        </a>
                    </div>
                </div>
            </main>
            <footer class="panel panel-default">
                <div class="panel-body">
                    <ul class="nav nav-pills">
                        <li>
                            <a href="http://gonapps.io/blog/admin">
                            <span class="glyphicon glyphicon-user"></span> Admin
                            </a>
                        </li>
                        <li>
                            <a href="http://gonapps.io/blog/posts">
                            <span class="glyphicon glyphicon-folder-open"></span> Archive
                            </a>
                        </li>
                        <li>
                            <a href="http://gonapps.io/blog/tags">
                            <span class="glyphicon glyphicon-tags"></span> Tags
                            </a>
                        </li>
                    </ul>
                    <hr/>
                    <ul class="nav nav-pills">
                        <li>
                            <a href="http://gonapps.io">
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
                </div>
            </footer>
        </div>
    </body>
</html>