<#import "parts/common_part.ftl" as common>
<#include "parts/security.ftl">

<@common.page>
    <link rel="stylesheet" href="static/css/orders_template.css" type="text/css">
    <table class="table table-bordered">
        <thead class="bold-lightgreen">
        <tr>
            <td width="50" class="text-center align-middle">#</td>
            <td width="150" class="text-center align-middle">Date of creation</td>
            <td width="150" class="text-center align-middle">Product</td>
            <td width="100" class="text-center align-middle">Total price</td>
            <td width="100" class="text-center align-middle">Quantity</td>
            <td width="100" class="text-center align-middle">Customer name</td>
            <td width="100" class="text-center align-middle">Status</td>
        </tr>
        </thead>
        <tbody>
        <#list orders as order>
            <tr>
                <td width="50" class="text-center align-middle">
                    <b>${order?index+1}</b>
                </td>
                <td width="150" class="text-center align-middle">
                    <b>${order.dateCreated?datetime?string("dd-MM-yyyy")}</b>
                </td>
                <td width=150" class="text-center align-middle">
                    <p>
                        <a style="color: black" href="order-${order.id}">
                            <#if order.boardGame.filename??>
                                <img width="150" height="150" src="images/${order.boardGame.filename}"
                                     alt="image board game">
                            <#else>
                                <b>Details</b>
                            </#if>
                        </a>
                    </p>
                </td>
                <td width="100" class="text-center align-middle">
                    <b>${order.boardGame.price*order.quantity} $</b>
                </td>
                <td width="100" class="text-center align-middle">
                    <b>${order.quantity} x</b>
                </td>
                <td width="100" class="text-center align-middle">
                    <b>${order.customerName}</b>
                </td>
                <td width="100" class="text-center align-middle">
                    <b>${order.status}</b>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

</@common.page>