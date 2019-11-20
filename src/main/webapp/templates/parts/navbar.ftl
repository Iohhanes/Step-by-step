<#include "security.ftl">
<#import "logout.ftl" as logout>

<nav class="navbar navbar-expand-lg navbar-light">
    <a class="navbar-brand" href="">Step by Step</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item ">
                <a class="nav-link" href="">Home<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="store">Store</a>
            </li>
            <#if user??>
                <li class="nav-item ">
                    <a class="nav-link" href="basket">Cart<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item ">
                    <a class="nav-link" href="orders">Orders<span class="sr-only">(current)</span></a>
                </li>
            </#if>
        </ul>
        <div class="navbar-text mr-3">${name}</div>
        <div>
            <#if isActive>
                <@logout.page/>
            <#else>
                <form action="login" method="post">
<#--                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
                    <button class="btn" type="submit">Sign In</button>
                </form>
            </#if>
        </div>
</nav>