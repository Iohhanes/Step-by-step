<#import "parts/common_part.ftl" as common>

<@common.page>
    <div class="row">
        <div class="col-sm">
            <#if boardGame.filename??>
                <img width="400" height="400" src="images/${boardGame.filename}" alt="image board game">
            </#if>
        </div>
        <div class="col-sm">
                <div class="text-center">
                    <b>${boardGame.title}</b>
                    <b>${boardGame.description}</b>
                    <b>Price: ${boardGame.price} $</b>
                    <b>Count of players: ${boardGame.countPlayers}</b>
                    <b>Age: ${boardGame.age}+</b>
                    <a class="btn primary" href="store">Back to Store</a>
                </div>
        </div>
    </div>
</@common.page>