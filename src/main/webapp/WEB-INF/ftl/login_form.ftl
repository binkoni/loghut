<#include "header.ftl"/>
<form action="${settings.getSetting('admin.url')}/login.do" method="POST">
    <h2>Login Required</h2>
    <label for="id">ID</label>
    <br/>
    <input name="id" type="text"/>
    <br/>
    <label for="password">PASSWORD</label>
    <br/>
    <input name="password" type="password"/>
    <br/>
    <br/>    
    <#if requestPath??>
        <input type="hidden" name="request_path" value="${requestPath}"/>
    </#if>
    <input value="Login" type="submit"/>
</form>
<#include "footer.ftl"/>