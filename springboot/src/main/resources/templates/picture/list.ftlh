<html>
<#include "../component/head.ftlh">

<body>
<div id="wrapper" class="toggled">
    <#--内容区域头部样式-->
    <h2 class="bg-info text-white p-3  text-center">轮播图管理</h2>
    <#--左边栏-->
    <#include "../component/left.ftlh">


    <#--主要内容content-->
    <div id="page-content-wrapper">
        <#--添加轮播图-->
        <button class="btn btn-warning text-white my-3 offset-md-1"
                onclick="window.parent.location.href='/diancan/picture/index';">
            添加轮播图
        </button>
        <div class="row clearfix">
            <div class="col-md-10 offset-md-1 column">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr class="table-info">
                        <th>图片id</th>
                        <th>图片描述</th>
                        <th>图片</th>
                        <th>图片url</th>
                        <th>创建时间</th>
                        <th colspan="2">操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#list categoryList as category>
                        <tr>
                            <td>${category.picId}</td>
                            <td>${category.picMessage}</td>
                            <td><img height="50" width="50" src="${category.picUrl}" alt=""></td>
                            <td>${category.picUrl}</td>
                            <td>${category.createTime}</td>
                            <td><a href="/diancan/picture/index?picId=${category.picId}">修改</a></td>
                            <td><a href="/diancan/picture/remove?picId=${category.picId}">删除</a></td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>
</body>
</html>