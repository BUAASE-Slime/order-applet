<html>
<#include "../component/head.ftlh">

<body>
<div id="wrapper" class="toggled">
    <#--内容区域头部样式-->
    <h2 class="bg-info text-white p-3  text-center">轮播图添加/修改</h2>
    <#--左边栏-->
    <#include "../component/left.ftlh">

    <#--主要内容content-->
    <div id="page-content-wrapper" class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <form role="form" method="post" action="/diancan/picture/save">
                    <div class="form-group">
                        <label>图片url</label>
                        <input name="picUrl" type="text" class="form-control"
                               value="${(category.picUrl)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>图片描述</label>
                        <input name="picMessage" type="text" class="form-control"
                               value="${(category.picMessage)!''}"/>
                    </div>
                    <input hidden type="text" name="picId"
                           value="${(category.picId)!''}">
                    <button type="submit" class="btn  btn-info">提交</button>
                </form>
            </div>
        </div>
    </div>

</div>
</body>
</html>