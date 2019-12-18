<#import "parts/common_part.ftl" as common>

<@common.page>
    <link rel="stylesheet" href="static/css/item_profile_template.css" type="text/css">
    <div class="row">
        <div class="col-sm">
            <#if cartItem.boardGame.filename??>
                <img width="400" height="400" src="images/${cartItem.boardGame.filename}" alt="image board game">
            </#if>
        </div>
        <div class="col-sm">
            <div class="container">
                <div class="row info">
                    <div class="col text-center">
                        <b>Title: ${cartItem.boardGame.title}</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Description: ${cartItem.boardGame.description}</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Price: ${cartItem.boardGame.price} $</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Count of players: ${cartItem.boardGame.countPlayers}</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Age: ${cartItem.boardGame.age}+</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Quantity: ${cartItem.quantity} x</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <a class="btn warning" href="cart">Back to Cart</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@common.page>