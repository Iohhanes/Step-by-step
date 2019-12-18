<#import "parts/common_part.ftl" as common>
<#import "parts/board_game_list.ftl" as board_game_list>
<#include "parts/security.ftl">

<@common.page>
    <link rel="stylesheet" href="static/css/store_template.css" type="text/css">
    <form action="sorting-alphabetically-board_games" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn success sort" type="submit">Sort alphabetically</button>
    </form>
    <#if isAdmin>
        <form action="posting-form" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn warning apply" type="submit">Post new</button>
        </form>
    </#if>
    <@board_game_list.page/>
</@common.page>