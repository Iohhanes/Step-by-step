<#import "parts/common_part.ftl" as common>
<#import "parts/board_game_form.ftl" as board_game_form>
<#import "/spring.ftl" as spring/>

<@common.page>
    <form action="<@spring.url '/postBoardGame'/>" method="post" enctype="multipart/form-data">
        <@board_game_form.page/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn warning apply" type="submit">
            Post
        </button>
    </form>
    <div class="changes-cancel">
        <a href="store" class="btn danger">Cancel</a>
    </div>
</@common.page>