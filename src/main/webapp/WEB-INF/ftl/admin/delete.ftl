<#include "header.ftl"/>
<#include "search_bar.ftl"/>
<p>
    <#if post.secretEnabled == true>${settings.getSetting('admin.url')}<#else>${settings.getSetting('post.url')}</#if>${post.path} deleted
</p>
<#include "footer.ftl"/>