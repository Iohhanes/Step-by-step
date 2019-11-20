<#macro page>
    <link>
    <div>
    <form action="logout" method="post">
<#--        <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        <button class="btn" type="submit">Sign Out</button>
    </form>
</#macro>