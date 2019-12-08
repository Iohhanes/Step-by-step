<#include "security.ftl">
<#import "logout.ftl" as logout>

<nav class="navbar navbar-expand-lg navbar-light">
    <a class="navbar-brand" href="/stepByStep/">Step by Step</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item ">
                <a class="nav-link" href="/stepByStep/">Home<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="store">Store<span class="sr-only">(current)</span></a>
            </li>
            <#if isActive && !isAdmin>
                <li class="nav-item ">
                    <a class="nav-link" href="cart">Cart<span class="sr-only">(current)</span></a>
                </li>
            </#if>
            <#if isActive>
                <li class="nav-item ">
                    <a class="nav-link" href="orders">Orders<span class="sr-only">(current)</span></a>
                </li>
            </#if>
        </ul>
        <div class="navbar-text mr-3" style="color: lightgreen">${name}</div>
        <div>
            <#if isActive>
                <@logout.page/>
            <#else>
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item ">
                        <a class="nav-link" href="login">Sign in</a>
                    </li>
                </ul>
            </#if>
        </div>
    </div>
</nav>