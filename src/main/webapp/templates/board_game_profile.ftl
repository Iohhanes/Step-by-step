<#import "parts/common_part.ftl" as common>
<#include "parts/security.ftl">

<@common.page>
    <link rel="stylesheet" href="static/css/item_profile_template.css" type="text/css">
    <div class="row">
    <div class="col-sm">
        <#if boardGame.filename??>
            <img width="400" height="400" src="images/${boardGame.filename}" alt="image board game">
        </#if>
    </div>
    <div class="col-sm">
        <div class="container">
            <div class="row info">
                <div class="col text-center">
                    <b>Title: ${boardGame.title}</b>
                </div>
            </div>
            <div class="row info">
                <div class="col text-center">
                    <b>Description: ${boardGame.description}</b>
                </div>
            </div>
            <div class="row info">
                <div class="col text-center">
                    <b>Price: ${boardGame.price} $</b>
                </div>
            </div>
            <div class="row info">
                <div class="col text-center">
                    <b>Count of players: ${boardGame.countPlayers}</b>
                </div>
            </div>
            <div class="row info">
                <div class="col text-center">
                    <b>Age: ${boardGame.age}+</b>
                </div>
            </div>
            <div class="row info">
                <#if !isAdmin>
                    <div class="col text-center">
                        <form action="adding-board-game-to-user-cart" method="post">
                            <input type="hidden" name="boardGameId" value="${boardGame.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group row" style="margin-bottom: 5px">
                                <input style="margin-left: 70px" id="quantityId" type="number" name="quantity" min="1"
                                       max="2147483646" step="1" value="1" pattern="\d+"/>
                                <label for="quantityId"> x</label>
                            </div>
                            <button class="btn success" type="submit">Add to Cart</button>
                        </form>
                    </div>
                </#if>
                <div class="col text-center">
                    <a class="btn warning" href="store">Back to Store</a>
                </div>
            </div>
        </div>
    </div>
</@common.page>