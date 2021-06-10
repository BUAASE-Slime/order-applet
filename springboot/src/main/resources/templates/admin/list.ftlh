<html>
<#include "../component/head.ftlh">

<body>
<div id="wrapper" class="toggled">
    <#--内容区域头部样式-->
    <h2 class="bg-info text-white p-3  text-center">餐厅管理员管理</h2>
    <#--左边栏-->
    <#include "../component/left.ftlh">
    <#--主要内容content-->
    <#if !isAdmin??>
        <h2 class="text-danger m-5">您不是管理员，没有管理权限</h2>
    <#else >
        <div id="page-content-wrapper" class="container">
            <#--添加管理员-->
            <button class="btn btn-warning text-white my-3"
                    onclick="window.parent.location.href='/diancan/admin/index';">
                添加管理员
            </button>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr class="table-info">
                            <th>id</th>
                            <th>名字</th>
                            <th>密码</th>
                            <th>手机号/微信</th>
                            <th>类型</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list adminList as adminBean>
                            <tr>
                                <td>${adminBean.adminId}</td>
                                <td>${adminBean.username}</td>
                                <td>${adminBean.password}</td>
                                <td>${adminBean.phone}</td>
                                <td>${adminBean.getAdminStatusEnum().message}</td>
                                <td>${adminBean.createTime}</td>
                                <td>${adminBean.updateTime}</td>
                                <td><a href="/diancan/admin/index?adminId=${adminBean.adminId}">修改</a></td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </#if>

</div>
</body>
</html>