<html>
<#include "../component/head.ftlh">
<body>
<div id="wrapper" class="toggled">
    <#--内容区域头部样式-->
    <h2 class="bg-info text-white p-3  text-center">批量导入菜品</h2>
    <#--左边栏-->
    <#include "../component/left.ftlh">

    <#--主要内容content-->
    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-md-8 offset-1 column">
                <form action="/diancan/food/uploadExcel" method="post"
                      enctype="multipart/form-data">
                    <input class="form-control-lg mt-5 mb-2" type="file" name="file">
                    <p><input class="btn-lg btn-success " type="submit" value="提交"></p>
                </form>
                <h1>注意事项</h1>
                <p>1，文件必须是excel</p>
                <p>2，excel内容的列必须和下图一一对应，下图仅供参考</p>
                <img class="img-responsive img-lg" src="/diancan/img/shangpin.png"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>