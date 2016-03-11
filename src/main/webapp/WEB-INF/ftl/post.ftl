<!DOCTYPE html>
<html>
    <head>
        <title>${post.title} - Gon's Blog - gonapps.io</title>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,user-scalable=yes"/>
        <meta name="keywords" content="<#list post.tags as tag>${tag.name}<#sep>,</#sep></#list>"/>
        <link href="http://gonapps.io/res/favicon.gif" rel="shortcut icon" type="image/x-icon"/>
        <link href="http://gonapps.io/res/responsive_grid_system.css" rel="stylesheet" type="text/css"/>
        <link href="http://gonapps.io/blog/res/blog.css" rel="stylesheet" type="text/css"/>
        <script src="//code.jquery.com/jquery.min.js" type="text/javascript"></script>
        <script src="http://gonapps.io/blog/js/blog.js" type="text/javascript"></script>
    </head>
    <body>
    <script>
        document.addEventListener("click", function(event) {
            if(event.target.innerHTML == "delete" && confirm("Do you really want to delete this post?") == false) {
                event.preventDefault();
            }
        });
    </script>
        <header>
            <div id="header_blank" class="span12"></div>
            <div id="menu"></div>
            <div id="menu_button"></div>
            <div id="blog_title" class="span12">
                <div class="span5_center">
                    <a href="http://gonapps.io/blog"><img src="http://gonapps.io/blog/res/blog_title.svg"/></a>
                </div>
                <hr class="red_hr"/>
            </div>
            <div id="loghut-post-title" class="span12 text_align" style="font-size:3rem">${post.title}</div>
            <hr class="black_hr"/>
        </header>
        <main>
            <div class="span8_center" style="overflow:hidden">
                <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                <!-- responsive -->
                <ins class="adsbygoogle"
                     style="display:block"
                     data-ad-client="ca-pub-8314195422970862"
                     data-ad-slot="5857157239"
                     data-ad-format="auto"></ins>
                <script>
                (adsbygoogle = window.adsbygoogle || []).push({});
                </script>
            </div>
            <article id="loghut-post-text" class="span10_center" style="background-color:white;border:0.5rem solid black;padding:2.5%">
                ${post.text}
            </article>
            <div id="loghut-post-tags" class="span10_center vertical_align_tops" style="padding-left:2.5%;padding-right:2.5%;border-left:0.5rem solid black;border-right:0.5rem solid black;border-bottom:0.5rem solid black">
                Tags: 
                <#list post.tags as tag>
                    <a href="${settings.getSetting('tag.url')}${tag.path}">${tag.name}</a>
                </#list>
            </div>
            <div class="span10_center vertical_align_tops" style="padding-left:2.5%;padding-right:2.5%;border-left:0.5rem solid black;border-right:0.5rem solid black;border-bottom:0.5rem solid black">
                <a href="${settings.getSetting('admin.url')}/create.do">create</a>
                <a href="${settings.getSetting('admin.url')}${post.modificationFormPath}">modify</a>
                <a href="${settings.getSetting('admin.url')}${post.deletePath}">delete</a>
            </div>
                <div class="span8_center" style="overflow:hidden">
                <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                <!-- responsive2 -->
                <ins class="adsbygoogle"
                     style="display:block"
                     data-ad-client="ca-pub-8314195422970862"
                     data-ad-slot="2072759233"
                     data-ad-format="auto"></ins>
                <script>
                (adsbygoogle = window.adsbygoogle || []).push({});
                </script>
                </div>
            <div id="disqus_thread" class="span10_center">
                <script type="text/javascript">
                    /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
                    var disqus_shortname = 'gonapps'; // Required - Replace '<example>' with your forum shortname
                    /* * * DON'T EDIT BELOW THIS LINE * * */
                    (function() {
                        var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
                        dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
                        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
                    })();
                </script>
            </div>
            <div class="span8_center" style="overflow:hidden">
                <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                <!-- responsive3 -->
                <ins class="adsbygoogle"
                     style="display:block"
                     data-ad-client="ca-pub-8314195422970862"
                     data-ad-slot="3549492432"
                     data-ad-format="auto"></ins>
                <script>
                (adsbygoogle = window.adsbygoogle || []).push({});
                </script>
            </div>
        </main>
        <footer>
            <li id="profile" class="not_contained">
                <div>Profile</div>
                <img style="width:10rem" src="http://gonapps.io/res/wawa.jpg"/>
                <p>Hello my name is ***</p>
            </li>
            <ul>
                <li>
                    <a href="http://gonapps.io/blog/admin">Admin</a>
                </li>
                <li>
                    <a href="http://gonapps.io/blog/posts">Archive</a>
                </li>
                <li>
                    <a href="http://gonapps.io/blog/tags">Tags</a>
                </li>
            </ul>
            <hr class="yellow_hr"/>
            <ul>
                <li><a href="http://gonapps.io">Home</a></li>
                <li><a href="http://gonapps.io/apps">Gon Apps</a></li>
                <li><a href="https://kiwiirc.com/client/irc.freenode.net/#gonapps">IRC Chat</a></li>
                <li><a href="http://gonapps.io/about.html">About</a></li>
                <li>
                    <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
                        <input type="hidden" name="cmd" value="_s-xclick"/>
                        <input type="hidden" name="hosted_button_id" value="ZEJU5LY7WNVZ4"/>
                        <input type="image" name="submit" alt="Donate with PayPal"/>
                    </form>
                </li>
            </ul>
            <div id="copyright" class="span12"></div>
        </footer>
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-66175317-1', 'auto');
      ga('send', 'pageview');

    </script>
    </body>
</html>