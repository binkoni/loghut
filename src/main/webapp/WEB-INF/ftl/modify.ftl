<#include "header.ftl"/>
<#include "search_bar.ftl"/>
<p>
    <a href="<#if post.secretEnabled == true>${settings.getSetting('admin.url')}<#else>${settings.getSetting('post.url')}</#if>${post.path}">
        <#if post.secretEnabled == true>${settings.getSetting('admin.url')}<#else>${settings.getSetting('post.url')}</#if>${post.path}
    </a> modified
</p>
<#include "footer.ftl"/>