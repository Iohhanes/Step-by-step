<#import "parts/common_part.ftl" as common>

<@common.page>
    <link rel="stylesheet" href="static/css/login_template.css" type="text/css">
    <form action="loginProcessing" method="post">
        <div class="col-sm-4 text-center first-input">
            <input type="text" name="username" class="form-control" placeholder="Username"/>
        </div>
        <div class="col-sm-4 text-center">
            <input type="password" name="password" class="form-control" placeholder="Password"/>
        </div>
        <div class="col-sm-4 text-center">
            <#if loginDataError??>
                <div class="alert alert-danger">
                    ${loginDataError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn success sign-in" type="submit">
            Sign In
        </button>
        <div class="registration-ref">
            <a href="registration">New user</a>
        </div>
    </form>
</@common.page>