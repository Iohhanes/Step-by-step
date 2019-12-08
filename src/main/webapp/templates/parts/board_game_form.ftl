<#import "/spring.ftl" as spring/>

<#macro page>
    <fieldset>
        <link rel="stylesheet" href="static/css/board_game_form_template.css" type="text/css">
        <div class="col-sm-4 text-center first-input">
            <@spring.bind "boardGameForm.title"/>
            <input type="text" name="${spring.status.expression}" value="${spring.status.value!}" class="form-control"
                   placeholder="Title"/>
            <#if spring.status.error>
                <div class="alert alert-danger">
                    ${spring.status.errorCode!}
                </div>
            </#if>
        </div>
        <div class="col-sm-4 text-center">
            <@spring.bind "boardGameForm.description"/>
            <textarea class="form-control" rows="5" name="${spring.status.expression}" value="${spring.status.value!}"
                      placeholder="description"></textarea>
            <#if spring.status.error>
                <div class="alert alert-danger">
                    ${spring.status.errorCode!}
                </div>
            </#if>
        </div>
        <div class="col-sm-4 text-center">
            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
        </div>
        <div class="col-sm-4 text-center">
            <div class="form-group row">
                <label for="priceId">Price:   </label>
                <@spring.bind "boardGameForm.price"/>
                <input id="priceId" type="number" name="${spring.status.expression}" value="${spring.status.value!}"
                       min="0.01" max="1500.00" step="0.01"/>
                <label>   $</label>
            </div>
            <#if spring.status.error>
                <div class="alert alert-danger">
                    ${spring.status.errorCode!}
                </div>
            </#if>
        </div>
        <div class="col-sm-4 text-center">
            <div class="form-group row">
                <label for="countPlayersId">Count of players:   </label>
                <@spring.bind "boardGameForm.countPlayers"/>
                <input id="countPlayersId" type="number" name="${spring.status.expression}"
                       value="${spring.status.value!}"
                       min="2" max="8" step="1" pattern="\d+"/>
            </div>
            <#if spring.status.error>
                <div class="alert alert-danger">
                    ${spring.status.errorCode!}
                </div>
            </#if>
        </div>
        <div class="col-sm-4 text-center">
            <div class="form-group row">
                <label for="ageId">Age:   </label>
                <@spring.bind "boardGameForm.age"/>
                <input id="ageId" type="number" name="${spring.status.expression}" value="${spring.status.value!}"
                       min="0" max="18" step="1" pattern="\d+"/>
            </div>
            <#if spring.status.error>
                <div class="alert alert-danger">
                    ${spring.status.errorCode!}
                </div>
            </#if>
        </div>
    </fieldset>
</#macro>