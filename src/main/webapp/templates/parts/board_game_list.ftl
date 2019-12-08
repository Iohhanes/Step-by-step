<#include "security.ftl">

<#macro page>
    <div class="template-search" style="margin-bottom: 15px">
        <a class="btn primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
           aria-controls="collapseExample">Find by</a>
    </div>
    <div class="collapse" id="collapseExample">
        <form action="findBoardGame" method="post">
            <div class="col-sm-4 text-center">
                <div class="form-group row">
                    <input type="text" name="title" class="form-control" placeholder="Title"/>
                </div>
                <div class="form-group row">
                    <label for="priceId">Price: </label>
                    <input id="priceId" type="number" name="price" value="" min="0.01" max="1500.00" step="0.01"/>
                    <label> $</label>
                </div>
                <div class="form-group row">
                    <label for="countPlayersId">Count of players: </label>
                    <input id="countPlayersId" type="number" name="countPlayers" value="" min="2" max="8" step="1"
                           pattern="\d+"/>
                </div>
                <div class="form-group row">
                    <label for="ageId">Age: </label>
                    <input id="ageId" type="number" name="age" value="" min="0" max="18" step="1" pattern="\d+"/>
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn primary find" type="submit">Find</button>
        </form>
    </div>
    <table class="table table-hover">
        <tbody>
        <#list boardGames as boardGame>
            <tr>
                <td width="150" class="align-middle">
                    <p>
                        <a href="boardGame-${boardGame.id}">
                            <#if boardGame.filename??>
                                <img width="150" height="150" src="images/${boardGame.filename}" alt="image board game">
                            </#if>
                        </a>
                    </p>
                </td>
                <td width="200" class="text-center align-middle">
                    <b>${boardGame.title}</b>
                </td>
                <td width="200" class="text-center align-middle">
                    <b>${boardGame.price} $</b>
                </td>
                <#if isActive && !isAdmin>
                    <td width="50" class="align-middle">
                        <div class="text-center">
                            <form action="addItemToUserCart" method="post">
                                <input type="hidden" name="boardGameId" value="${boardGame.id}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="form-group row" style="margin-bottom: 5px">
                                    <input id="quantityId" type="number" name="quantity" min="1" max="2147483646"
                                           step="1" value="1" pattern="\d+"/>
                                    <label for="quantityId"> x</label>
                                </div>
                                <button class="btn success" type="submit">Add to Cart</button>
                            </form>
                        </div>
                    </td>
                    <#if invalidQuantityError??>
                        <td width="50" class="align-middle">
                            <div class="text-center">
                                <div class="alert alert-danger">
                                    ${invalidQuantityError}
                                </div>
                            </div>
                        </td>
                    </#if>
                </#if>
                <#if isAdmin>
                    <td width="50" class="align-middle">
                        <div class="text-center">
                            <form action="showEditingForm" method="post">
                                <input type="hidden" name="boardGameId" value="${boardGame.id}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button class="btn warning" type="submit">Edit</button>
                            </form>
                        </div>
                    </td>
                    <td width="50" class="align-middle">
                        <div class="text-center">
                            <form action="deleteBoardGame" method="post">
                                <input type="hidden" name="boardGameId" value="${boardGame.id}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button class="btn danger" type="submit">Delete</button>
                            </form>
                        </div>
                    </td>
                </#if>
            </tr>
        <#else>
            <div class="text-center">
                Board Games not found
            </div>
        </#list>
        </tbody>
    </table>
</#macro>