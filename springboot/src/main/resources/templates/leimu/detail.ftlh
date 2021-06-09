<html>
<#include "../component/head.ftlh">

<body>
<div id="wrapper" class="toggled">
    <#--内容区域头部样式-->
    <h2 class="bg-info text-white p-3  text-center">添加菜品类目</h2>
    <#--左边栏-->
    <#include "../component/left.ftlh">
    <#--主要内容content-->
    <div id="page-content-wrapper" class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <form role="form" method="post" action="/diancan/leimu/save">
                    <div class="form-group">
                        <label>类目名</label>
                        <input name="leimuName" type="text" class="form-control"
                               value="${(leimu.leimuName)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>类目type</label>
                        <input name="leimuType" type="number" class="form-control"
                               value="${(leimu.leimuType)!''}"/>
                    </div>
                    <input hidden type="text" name="leimuId" value="${(leimu.leimuId)!''}">
                    <button type="submit" class="btn  btn-info">提交</button>
                </form>
            </div>
        </div>
    </div>

</div>
</body>
</html>