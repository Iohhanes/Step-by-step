<#import "parts/common_part.ftl" as common>
<#import "parts/cart_item_list.ftl" as cart_item_list>
<#include "parts/security.ftl">

<@common.page>
    <link rel="stylesheet" href="static/css/cart_template.css" type="text/css">
    <#if isActive && !isAdmin>
        <form action="clearUserCart" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn primary clear" type="submit">Clear cart</button>
        </form>
    </#if>
    <@cart_item_list.page/>
</@common.page>