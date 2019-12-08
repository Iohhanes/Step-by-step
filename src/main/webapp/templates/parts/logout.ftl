<#macro page>
    <div>
    <form action="logout" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn success" style="color: azure" type="submit">Sign Out</button>
    </form>
</#macro>