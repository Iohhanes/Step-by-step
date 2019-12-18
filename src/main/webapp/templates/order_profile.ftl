<#import "parts/common_part.ftl" as common>
<#include "parts/security.ftl">

<@common.page>
    <link rel="stylesheet" href="static/css/item_profile_template.css" type="text/css">
    <div class="row">
        <div class="col-sm">
            <#if order.boardGame.filename??>
                <img width="400" height="400" src="images/${order.boardGame.filename}" alt="image board game">
            </#if>
        </div>
        <div class="col-sm">
            <div class="container">
                <div class="row info">
                    <div class="col text-center">
                        <b>Title: ${order.boardGame.title}</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Total price: ${order.boardGame.price*order.quantity} $</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Quantity: ${order.quantity} x</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Customer name: ${order.customerName}</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Customer email: ${order.customerEmail}</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Customer phone: ${order.customerPhone}</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <b>Status: ${order.status}</b>
                    </div>
                </div>
                <div class="row info">
                    <div class="col text-center">
                        <#if !isAdmin && isActive>
                            <a class="btn warning" href="orders-history">Back to Orders History</a>
                        </#if>
                        <#if isAdmin>
                            <a class="btn warning" href="orders-management">Back to Orders Management</a>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@common.page>