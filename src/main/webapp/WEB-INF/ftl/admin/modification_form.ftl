<#include "header.ftl"/>
<#include "search_bar.ftl"/>
<h1>Modify Post</h1>
<form class="span10_center" action="${settings.getSetting('admin.url')}/modify.do" method="post">
    <input type="hidden" name="year" value="${post.year?c}"/>
    <input type="hidden" name="month" value="${post.month?c}"/>
    <input type="hidden" name="day" value="${post.day?c}"/>
    <input type="hidden" name="number" value="${post.number?c}"/>
    <input type="hidden" name="old_secret_enabled" value="${post.secretEnabled?c}"/>
    <label for="title">Title</label>
    <input style="width:100%" type="text" id="title" name="title" value="${post.title}"/>
    <input type="checkbox" id="new_secret_enabled" name="new_secret_enabled" <#if post.secretEnabled == true>checked</#if>/>
    <label for="new_secret_enabled">Secret</label><br/>
    <label for="tags">Tags</label>
    <input style="width:100%" type="text" id="tags" name="tag_names" value="<#list post.tags as tag>${tag.name}<#sep>,</#sep></#list>"/>
    <label for="text">Text</label>
    <textarea class="ckeditor" style="width:100%;height:30rem" id="text" name="text">${post.text}</textarea>
    <input style="float:right;display:inline-block" type="submit" value="Modify"/>
</form>
<script src="//cdn.ckeditor.com/4.5.2/standard/ckeditor.js" type="text/javascript"></script>
<script type="text/javascript">
CKEDITOR.config.allowedContent = true;
CKEDITOR.config.height = 500;
</script>
<#include "footer.ftl"/>