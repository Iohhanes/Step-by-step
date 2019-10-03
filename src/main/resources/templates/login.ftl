<#import "parts/common_part.ftl" as common>

<@common.page>
    <link rel="stylesheet" href="/static/login_template.css" type="text/css">
    <form action="/login" method="post">
        <div class="col-sm-4 text-center first-input">
            <input type="text" name="username" class="form-control" placeholder="Username"/>
        </div>
        <div class="col-sm-4 text-center">
            <input type="password" name="password" class="form-control" placeholder="Password"/>
            <#--                <#if error??>-->
            <#--                    <div class="alert alert-danger">-->
            <#--                        ${error}-->
            <#--                    </div>-->
            <#--                </#if>-->
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn sign-in" type="submit">
            Sign In
        </button>
        <div class="registration-ref">
            <a href="/registration">New user</a>
        </div>
    </form>
</@common.page>