<#import "parts/common_part.ftl" as common>
<#import "parts/board_game_list.ftl" as board_game_list>
<#include "parts/security.ftl">

<@common.page>
    <#if isAdmin>
        <form action="post_new" method="get">
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
            <button class="btn" type="submit">Post new</button>
        </form>
    </#if>
    <@board_game_list.page/>
</@common.page>