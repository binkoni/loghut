<#include "header.ftl"/>
<#include "search_bar.ftl"/>
<table class="table table-striped table-hover table-bordered">
    <tbody>
        <tr>
            <td>request.authType</td>
            <td>${request.authType!}</td>
        </tr>
        <tr>
            <td>request.contextPath</td>
            <td>${request.contextPath!}</td>
        </tr>
        <tr>
            <td>request.characterEncoding</td>
            <td>${request.characterEncoding!}</td>
        </tr>
        <tr>
            <td>request.method</td>
            <td>${request.method!}</td>
        </tr>
        <tr>
            <td>request.pathInfo</td>
            <td>${request.pathInfo!}</td>
        </tr>
        <tr>
            <td>request.pathTranslated</td>
            <td>${request.pathTranslated!}</td>
        </tr>
        <tr>
            <td>request.queryString</td>
            <td>${request.queryString!}</td>
        </tr>
        <tr>
            <td>request.remoteUser</td>
            <td>${request.remoteUser!}</td>
        </tr>
        <tr>
            <td>request.requestedSessionId</td>
            <td>${request.requestedSessionId!}</td>
        </tr>
        <tr>
            <td>request.requestURI</td>
            <td>${request.requestURI!}</td>
        </tr>
        <tr>
            <td>request.requestURL</td>
            <td>${request.requestURL!}</td>
        </tr>
        <tr>
            <td>request.servletPath</td>
            <td>${request.servletPath!}</td>
        </tr>
        <#list request.headerNames as headerName>
            <tr>
                <td>${headerName}</td>
                <#list request.getHeaders(headerName) as header>
                    <td>${header}</td>
                </#list>
            </tr>
        </#list>
    </tbody>
</table>
<#include "footer.ftl"/>