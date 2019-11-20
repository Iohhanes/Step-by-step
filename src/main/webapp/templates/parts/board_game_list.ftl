<#include "security.ftl">

<#macro page>
    <table class="table table-hover">
        <tbody>
        <#list boardGames as boardGame>
            <tr>
                <td width="150" class="align-middle">
                    <a style="display: block;" href="/products/${product.id}">
                        <#if boardGame.fileName??>
                            <img width="150" height="150" src="/img/${boardGame.fileName}" alt="image board game">
                        </#if>
                    </a>
                </td>
                <td width="400" class="text-center align-middle">
                    <p>${boardGame.title}</p>
                    <p>${boardGame.description}</p>
                </td>
                <#if isAdmin>
                    <td width="50" class="align-middle">
                        <div class="text-center">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <a href="/delete-product-" class="btn btn-danger">Edit</a>
                        </div>
                    </td>
                    <td width="50" class="align-middle">
                        <div class="text-center">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <a href="/delete-product-" class="btn btn-danger">Delete</a>
                        </div>
                    </td>
                </#if>
            </tr>
        <#else>
            Board Games not found
        </#list>
        </tbody>
    </table>
</#macro>