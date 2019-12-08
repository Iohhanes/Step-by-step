<#import "parts/common_part.ftl" as common>
<#import "parts/board_game_list.ftl" as board_game_list>
<#include "parts/security.ftl">

<@common.page>
    <link rel="stylesheet" href="static/css/store_template.css" type="text/css">
    <form action="sortAlphabetically" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn primary sort" type="submit">Sort alphabetically</button>
    </form>
    <#if isAdmin>
        <form action="showPostingForm" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn warning apply" type="submit">Post new</button>
        </form>
    </#if>
    <@board_game_list.page/>
</@common.page>