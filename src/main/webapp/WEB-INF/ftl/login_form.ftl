<#include "header.ftl"/>
<div class="row">
<form class="form-group col-md-3" action="${settings.getSetting('admin.url')}/login.do" method="POST">
    <h2>Login Required</h2>
    <label for="id">ID</label>
    <input class="form-control" name="id" type="text"/>
    <label for="password">PASSWORD</label>
    <input class="form-control" name="password" type="password"/>    
    <#if requestPath??>
        <input type="hidden" name="request_path" value="${requestPath}"/>
    </#if>
    <input class="btn btn-default" value="Login" type="submit"/>
</form>
</div>
<#include "footer.ftl"/>