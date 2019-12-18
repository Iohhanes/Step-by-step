<#import "parts/common_part.ftl" as common>
<#import "/spring.ftl" as spring/>

<@common.page>
    <link rel="stylesheet" href="static/css/order_form_template.css" type="text/css">
    <form action="<@spring.url '/placing-order'/>" method="post">
        <fieldset>
            <div class="col-sm-4 text-center first-input">
                <@spring.bind "orderForm.customerName"/>
                <input type="text" name="${spring.status.expression}" value="${spring.status.value!}"
                       class="form-control"
                       placeholder="Customer name"/>
                <#if spring.status.error>
                    <div class="alert alert-danger">
                        ${spring.status.errorCode!}
                    </div>
                </#if>
            </div>
            <div class="col-sm-4 text-center">
                <@spring.bind "orderForm.customerEmail"/>
                <input type="email" class="form-control" name="${spring.status.expression}"
                       value="${spring.status.value!}"
                       placeholder="Customer email"></input>
                <#if spring.status.error>
                    <div class="alert alert-danger">
                        ${spring.status.errorCode!}
                    </div>
                </#if>
            </div>
            <div class="col-sm-4 text-center">
                <@spring.bind "orderForm.customerPhone"/>
                <input type="text" class="form-control" name="${spring.status.expression}"
                       value="${spring.status.value!}"
                       placeholder="Customer phone"></input>
                <#if spring.status.error>
                    <div class="alert alert-danger">
                        ${spring.status.errorCode!}
                    </div>
                </#if>
            </div>
            <div class="col-sm-4 text-center">
                <div class="form-group row">
                    <label for="quantityId">Quantity: </label>
                    <@spring.bind "orderForm.quantity"/>
                    <input id="quantityId" type="number" name="${spring.status.expression}"
                           value="${spring.status.value!}"
                           min="1" max="${cartItem.quantity}" step="1" pattern="\d+"/>
                    <label> x</label>
                </div>
                <#if spring.status.error>
                    <div class="alert alert-danger">
                        ${spring.status.errorCode!}
                    </div>
                </#if>
            </div>
        </fieldset>
        <input type="hidden" name="cartItemId" value="${cartItem.id}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn warning apply" type="submit">
            Place
        </button>
    </form>
    <div class="changes-cancel">
        <a href="cart" class="btn danger">Cancel</a>
    </div>
</@common.page>