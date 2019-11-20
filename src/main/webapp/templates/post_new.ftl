<#import "parts/common_part.ftl" as common>

<@common.page>
    <link rel="stylesheet" href="static/css/post_new_template.css" type="text/css">
    <form action="post_new" method="post" enctype="multipart/form-data">
        <div class="col-sm-4 text-center first-input">
            <input type="text" name="title" class="form-control" placeholder="Title"/>
        </div>
        <div class="form-group col-sm-4">
            <textarea class="form-control" rows="5" name="description" placeholder="description"></textarea>
        </div>
        <div class="input-group col-sm-4">
            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
        </div>
        <div form-group row>
            <label>Price:</label>
            <input type="number" name="price" min="0.00" max="1500.00" step="0.01" value="1.00"/>
            <label> $</label>
        </div>
        <div form-group row>
            <label>Count of players:</label>
            <input type="number" name="countPlayers" min="2" max="8" step="1" value="2"/>
        </div>
        <div form-group row>
            <label>Age:</label>
            <input type="number" name="age" min="0" max="18" step="1" value="0"/>
        </div>
<#--        <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        <button class="btn post" type="submit">
            Post
        </button>
    </form>
</@common.page>