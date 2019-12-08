<#import "parts/common_part.ftl" as common>
<#import "/spring.ftl" as spring/>

<@common.page>
    <link rel="stylesheet" href="static/css/login_template.css" type="text/css">
    <fieldset>
        <form action="<@spring.url '/registration'/>" method="post">
            <div class="col-sm-4 text-center first-input">
                <@spring.bind "userForm.username"/>
                <input type="text" name="${spring.status.expression}" value="${spring.status.value!}"
                    class="form-control" placeholder="Username"/>
                <#if spring.status.error>
                    <div class="alert alert-danger">
                        ${spring.status.errorCode!}
                    </div>
                </#if>
            </div>
            <div class="col-sm-4 text-center">
                <@spring.bind "userForm.password"/>
                <input type="password" name="${spring.status.expression}" value="${spring.status.value!}"
                   class="form-control" placeholder="Password"/>
                <#if spring.status.error>
                    <div class="alert alert-danger">
                        ${spring.status.errorCode!}
                    </div>
                </#if>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn success sign-in" type="submit">
                Register
            </button>
        </form>
    </fieldset>
</@common.page>