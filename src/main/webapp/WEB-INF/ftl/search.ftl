<#include "header.ftl"/>
<style type="text/css">  
    table {
         width:100%;
         border-collapse:collapse;
    }
    table th, table td {
        border:1px solid black;
    }
    table thead {
        background-color:#CCCCCC;
    }
    table tbody tr:nth-child(even) {
    }
</style>
<table>
    <thead>
        <tr> 
            <th>Title</th>
            <th>Tags</th>
            <th>YYYY</th>
            <th>MM</th>
            <th>DD</th>
        </tr>
    </thead>
    <tbody>
        <#list posts as post>
        <tr>
            <td>
                <a href="<#if post.secretEnabled == true>${settings.getSetting('admin.url')}<#else>${settings.getSetting('post.url')}</#if>${post.path}">
                    ${post.title}
                </a>
                <#if post.secretEnabled == true>
                    (secret)
                </#if>
                <br/>
                (
                    <a href="${settings.getSetting('admin.url')}${post.modificationFormPath}">modify</a>
                    <a href="${settings.getSetting('admin.url')}${post.deletePath}">delete</a>
                )
            </td>
            <td>
                <#list post.tags as tag>
                    <a href="${settings.getSetting('tag.url')}${tag.path}">${tag.name}</a>
                </#list>
            </td>
            <td>${post.year?c}</td>
            <td>${post.month?c}</td>
            <td>${post.day?c}</td>
        </tr>
        </#list>
    </tbody>
</table>
<#if previousPageLink??>
    <a href="${previousPageLink}">&lt;prev</a>
    &nbsp;
</#if>
[${currentPage}]
<#if nextPageLink??>
    &nbsp;
    <a href="${nextPageLink}">next&gt;</a>
</#if>
<script type="text/javascript">
    document.addEventListener("click", function(event) {
        if(event.target.innerHTML == "delete" && confirm("Do you really want to delete this post?") == false) {
            event.preventDefault();
        }
    });
</script>
<#include "footer.ftl"/>
