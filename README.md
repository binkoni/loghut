LogHut
==
<img src="http://i.imgur.com/jJH0lza.png" width="200px" height="200px"/><br/>
**LogHut** is a **static blog engine** written in **Java**.<br/>
You can run your blog even in Cubietruck or Raspberry Pi with no problems.<br>
You can customize your blog with Freemarker templates.<br/>
This project is still in development, you can also give some advice to the author to improve this application if you want.<br/>

How it works
--
First, let me clarify the meaning of 'static'.<br/>
The word 'static' means that all posts you write will be saved as html files, not in the database.<br/>
Being static, you can run your blog even in low-performance machines.<br/>
But 'static' doesn't mean that you should write posts, manage posts manually.<br/>
You can do the jobs very conveniently such as creating posts, modifying posts, deleting posts, managing tags, downloading the backup.<br/>

History of LogHut and A performance problem
--
When I was high school student I tried Naver blog, Google blog and Wordpress to run my own blog and make some money with Adsense.<br/><br/>
Actually none of them was satisfying, so I decided to create my own blog engine.<br/>
LogHut was initially written in Perl.<br/><br/>
I tried to make it lightweight and fast so that's why I choose Perl as the project language.<br/>
And I also made my own modules from server to html parser rather than depending on CPAN modules which result in small dependencies and fast performance.<br/><br/>
Until I made first buglessly-working Perl version of LogHut I didn't notice the most worst characteristic of Perl.<br/>
The problem was reference-count based garbage collecter in Perl.<br/><br/>
Which means that if there is a circular reference then the part of memory will never be garbage collected.<br/>
And it's worse than C++ because you can explicitly delete the reference!<br/><br/>
So in spite of being a script language and support of OOP, your web app development speed will be very slow and painful due to the memory management.<br/><br/>
Then I decided to rewrite LogHut with Java for fast performance, use Servlet, Spring, Freemarker for faster development.<br/>
But this has also a problem.<br/><br/>
Because OpenJDK Java virtual machine was too slow and due to a lot of dependencies, the application became very slower.<br/><br/>
Currently the performance is improved by caching and **you need to use JamVM rather than OpenJDK VM for better performance**.<br/>
With this improvements the performance of LogHut java version is almost fast as Perl version.<br/><br/>

How to use
--
1. Download .war file
2. Move .war file into /var/lib/tomcat7/webapps/ and extract it (or it will be extracted automatically by Tomcat)
3. Go to ${extractedDirectory}/WEB-INF/classes
4. Edit settings.properties file
5. Go to ${extractedDirectory}/WEB-INF/ftl/blog
6. Edit *.ftl files to customize your blog design
7. Restart tomcat7
8. Configure nginx properly
<br/>(Visit gonapps.io/blog and see how it works)

settings.properties example
--
admin.id=foo<br/>
admin.password=bar<br/>
session.timeout=3600<br/>
blog.url=/blog<br/>
admin.url=/blog/admin<br/>
posts.url=/blog/posts<br/>
tags.url=/blog/tags<br/>
blog.directory=/mnt/web/blog<br/>
posts.directory=/mnt/web/blog/posts<br/>
tags.directory=/mnt/web/blog/tags<br/>

nginx /etc/nginx/sites-enabled/default example
--
    location ~ ^/blog/admin$ {
        rewrite /blog/admin /blog/admin/ permanent;
    }

    location ~ /blog/admin/ {
        rewrite ^/blog/admin/(.*)$ /loghut/$1 break;
        proxy_pass http://127.0.0.1:8080;
        proxy_cookie_path /loghut /blog/admin;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

How to edit templates
--
Templates follow Freemarker syntax.<br/>
First, go to ${extractedDirectory}/WEB-INF/ftl/blog<br/>
<br/>
main_index.ftl will be //yourdomain/blog/index.html<br/>
    This template is provided with 'post' object, 'post' has following attributes:<br/>
        post.year -> int<br/>
        post.month -> int<br/>
        post.day -> int<br/>
        post.number -> int<br/>
        post.secretEnabled -> boolean<br/>
        post.path -> String<br/>
        post.title -> String<br/>
        post.text -> String<br/>
        post.tags -> List of 'tag' object<br/>
        post.modificationFormPath -> String<br/>
        post.deletePath -> String<br/>
<br/>
post.ftl will be //yourdomain/blog/posts/YYYY/MM/DD_NUMBER.html<br/>
    This template is provided with 'post' object.<br/>
<br/>
secret.ftl will be //yourdomain/blog/admin/secret.do?year=YEAR&month=MONTH&day=DAY&number=NUMBER<br/>
    This template is provided with 'post' object.<br/>
<br/>
year_index.ftl will be //yourdomain/blog/posts/index.html, //yourdomain/blog/tags/TAGNAME/index.html<br/>
    This template is provided with 'years' object. 'years' is List of String.<br/>
<br/>
month_index.ftl will be //yourdomain/blog/YYYY/index.html, //yourdomain/blog/tags/TAGNAME/YYYY/index.html<br/>
    This template is provided with 'months' object. 'months' is List of String.<br/>
<br/>
post_index.ftl will be //yourdomain/blog/YYYY/MM/index.html<br/>
    This template is provided with 'posts' object. 'posts' is List of 'post' object.<br/>
<br/>
tag_name_index.ftl //yourdomain/blog/tags/TAGNAME/index.html<br/>
    This template is provided with 'tagNames' object. 'tagNames' is List of String.<br/>
<br/>
tag_index.ftl //yourdomain/blog/tags/TAGNAME/YYYY/MM/index.html is provided with 'tags' object. 'tags' is List of 'tag' object. 'tag' has following attributes:<br/>
    tag.name -> String<br/>
    tag.path -> String<br/>
    tag.post -> 'post'<br/>

Author
---
Byeonggon Lee (gonny952@gmail.com)

License
---
>Copyright (c) 2015, Byeonggon Lee <gonny952@gmail.com>
><br/>
>This program is free software: you can redistribute it and/or modify
>it under the terms of the GNU General Public License as published by
>the Free Software Foundation, either version 3 of the License, or
>(at your option) any later version.
><br/>
>This program is distributed in the hope that it will be useful,
>but WITHOUT ANY WARRANTY; without even the implied warranty of
>MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
>GNU General Public License for more details.
><br/>
>You should have received a copy of the GNU General Public License
>along with this program.  If not, see <http://www.gnu.org/licenses/>.


Software dependencies
---
* Web server (nginx, apache, etc.)
* Tomcat7
* JRE >= 7

Supported operating systems
---
* Linux
* Maybe other Unices (Not tested)
