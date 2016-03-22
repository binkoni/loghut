<#include "header.ftl"/>
<#include "search_bar.ftl"/>
<p>
    <a href="<#if post.secretEnabled == true>${settings.getSetting('admin.url')}<#else>${settings.getSetting('posts.url')}</#if>${post.path}">
        <#if post.secretEnabled == true>${settings.getSetting('admin.url')}<#else>${settings.getSetting('posts.url')}</#if>${post.path}
    </a> created
</p>
<#include "footer.ftl"/>