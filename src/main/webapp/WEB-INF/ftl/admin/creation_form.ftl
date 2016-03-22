<#include "header.ftl"/>
<#include "search_bar.ftl"/>
<h1>New Post</h1>
<form class="span10_center" action="${settings.getSetting('admin.url')}/create.do" method="post">
    <label for="title">Title</label>
    <input name="title" type="text" style="width:100%"/>
    <input name="secret_enabled" type="checkbox"/>
    <label for="secret_enabled">Secret</label>
    <br/>
    <label for="tag_names">Tags</label>
    <input name="tag_names" type="text" style="width:100%"/>
    <label for="text">Text</label>
    <textarea name="text" class="ckeditor" style="width:100%;height:30rem">
    </textarea>
    <input value="Create" type="submit" style="float:right;display:inline-block"/>
</form>
<script src="//cdn.ckeditor.com/4.5.2/standard/ckeditor.js" type="text/javascript"></script>
<script type="text/javascript">
CKEDITOR.config.allowedContent = true;
CKEDITOR.config.height = 500;
</script>
<#include "footer.ftl"/>