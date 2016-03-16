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
<script type="text/javascript">
    document.addEventListener("click", function(event) {
        if(event.target.id == "post-delete" && confirm("Do you really want to delete this post?") == false) {
            event.preventDefault();
        }
    });
</script>
<table class="table table-striped table-hover">
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
                    <a id="post-delete" href="${settings.getSetting('admin.url')}${post.deletePath}">delete</a>
                )
            </td>
            <td>
                <#list post.tags as tag>
                    <a class="label label-default" href="${settings.getSetting('tag.url')}${tag.path}">${tag.name}</a>
                </#list>
            </td>
            <td>${post.year?c}</td>
            <td>${post.month?c}</td>
            <td>${post.day?c}</td>
        </tr>
        </#list>
    </tbody>
</table>
<ul class="pagination">
<#if previousPageLink??>
    <li><a class="previous" href="${previousPageLink}">&lt;prev</a></li>
    &nbsp;
</#if>
<li><a>${currentPage}</a></li>
<#if nextPageLink??>
    &nbsp;
    <li><a class="next" href="${nextPageLink}">next&gt;</a></li>
</#if>
</ul>
<#include "footer.ftl"/>
