<#include "header.ftl"/>
<script type="text/javascript">
    document.addEventListener("change", function(event) {
        if(event.target.id = "search-filter-select") {
            document
            .getElementById("search_filter")
            .setAttribute("value",
                event.target.options[event.target.selectedIndex].value); 
        }
    });
</script>
<form id="search-form" class="form-group" class="" action="${settings.getSetting('admin.url')}/search.do" method="get">
    <input type="hidden" name="action" value="search"/>
    <div class="row">
        <div class="col-md-2"> 
            <select id="search-filter-select" class="form-control" form="search-form">
                <option value="title" <#if searchFilter == "title">selected</#if>>Title</option>
                <option value="tag_names" <#if searchFilter == "tag_names">selected</#if>>TagNames</option>
                <option value="text" <#if searchFilter == "text">selected</#if>>Text</option>
                <option value="years" <#if searchFilter == "years">selected</#if>>Years</option>
                <option value="months" <#if searchFilter == "months">selected</#if>>Months</option>
                <option value="days" <#if searchFilter == "days">selected</#if>>Days</option>
            </select>
            <input id="search_filter" type="hidden" name="search_filter" value="${searchFilter}"/>
        </div>
        <div class="col-md-5">
            <input class="col-md-6 form-control" type="text" name="search_keyword" value="${searchKeyword}"/>
        </div>
        <div class="col-md-5">
            <input class="btn btn-default" type="submit" value="Search"/>
        </div>
    </div>
    <input type="hidden" name="page_unit" value="10"/>
    <input type="hidden" name="page" value="1"/>
</form>

<hr/>
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
                <a href="<#if post.secretEnabled == true>${settings.getSetting('admin.url')}<#else>${settings.getSetting('posts.url')}</#if>${post.path}">
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
                    <a class="label label-default" href="${settings.getSetting('tags.url')}${tag.path}">${tag.name}</a>
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
