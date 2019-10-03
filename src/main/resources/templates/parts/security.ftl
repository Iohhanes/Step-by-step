<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin=user.isAdmin()
    isActive = true
    >
<#else>
    <#assign
    name = ""
    isActive = false
    isAdmin=false
    >
</#if>