<#include "security.ftl">

<#macro page>
    <table class="table table-borderless" style="background-color: purple">
        <tbody>
            <tr>
                <td width="465" class="text-center align-middle">
                    <div class="form-group row" style="margin-bottom: 15px">
                        <b>Total cost: ${cart.totalCost} $</b>
                    </div>
                </td>
                <td width="465" class="text-center align-middle">
                    <div class="form-group row" style="margin-bottom: 15px">
                        <b>Total count of items: ${cart.totalCountItems} x</b>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
    <table class="table table-hover">
        <tbody>
        <#list cartItems as cartItem>
            <tr>
                <td width="150" class="align-middle">
                    <p>
                        <a style="color: black " href="cart-item-${cartItem.id}">
                            <#if cartItem.boardGame.filename??>
                                <img width="150" height="150" src="images/${cartItem.boardGame.filename}"
                                     alt="image board game">
                            <#else>
                                <b>Details</b>
                            </#if>
                        </a>
                    </p>
                </td>
                <td width="200" class="text-center align-middle">
                    <b>${cartItem.boardGame.title}</b>
                </td>
                <td width="100" class="text-center align-middle">
                    <b>${cartItem.boardGame.price} $</b>
                </td>
                <td width="100" class="text-center align-middle">
                    <b>${cartItem.quantity} x</b>
                </td>
                <#if isActive && !isAdmin>
                    <td width="50" class="align-middle">
                        <div class="text-center">
                            <form action="placing-from" method="post">
                                <input type="hidden" name="cartItemId" value="${cartItem.id}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button class="btn success" type="submit">Order</button>
                            </form>
                        </div>
                    </td>
                    <td width="50" class="align-middle">
                        <div class="text-center">
                            <form action="deleting-cart-item" method="post">
                                <input type="hidden" name="cartItemId" value="${cartItem.id}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="form-group row" style="margin-bottom: 5px">
                                    <input type="number" name="quantity" min="1" max="${cartItem.quantity!}"
                                           step="1" value="0" pattern="\d+"/>
                                    <label for="quantityId"> x</label>
                                </div>
                                <button class="btn danger" type="submit">Delete</button>
                            </form>
                        </div>
                    </td>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</#macro>