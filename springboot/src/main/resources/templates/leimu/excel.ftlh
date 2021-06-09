<html>
<#include "../component/head.ftlh">
<body>
<div id="wrapper" class="toggled">
    <#--内容区域头部样式-->
    <h2 class="bg-info text-white p-3  text-center">批量导入菜品类目</h2>
    <#--左边栏-->
    <#include "../component/left.ftlh">

    <#--主要内容content-->
    <div id="page-content-wrapper" class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <form action="/diancan/leimu/uploadExcel" method="post"
                      enctype="multipart/form-data" class="mt-5">
                    <input class="input-lg" type="file" name="file">
                    <p><input class="btn-lg btn-success mt-2" type="submit" value="提交"></p>
                </form>
                <h1>注意事项</h1>
                <p>1，文件必须是excel</p>
                <p>2，excel内容的列必须和下图一一对应，下图仅供参考</p>
                <img class="img-responsive img-xs" src="/diancan/img/leimu.png"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>