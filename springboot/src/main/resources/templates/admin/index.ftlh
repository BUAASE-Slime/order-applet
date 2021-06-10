<html>
<#include "../component/head.ftlh">

<body>
<div id="wrapper" class="toggled">
    <#--内容区域头部样式-->
    <h2 class="bg-info text-white p-3  text-center">餐厅管理员修改/添加</h2>
    <#--左边栏-->
    <#include "../component/left.ftlh">
    <#--主要内容content-->
    <div id="page-content-wrapper" class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <form role="form" method="post" action="/diancan/admin/save">
                    <div class="form-group">
                        <label>名字</label>
                        <input name="username" type="text" class="form-control"
                               value="${(adminInfo.username)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>手机号/微信</label>
                        <input name="phone" type="text" class="form-control"
                               value="${(adminInfo.phone)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input name="password" type="text" class="form-control"
                               value="${(adminInfo.password)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>管理员权限设置</label>
                        <select name="adminType" class="form-control">
                            <#list enums as val>
                                <option value="${val.getCode()}"
                                        <#if (adminInfo.adminType)?? && adminInfo.adminType == val.getCode()>
                                            selected
                                        </#if>
                                >${val.getMessage()}
                                </option>
                            </#list>
                        </select>
                    </div>
                    <input hidden type="text" name="adminId"
                           value="${(adminInfo.adminId)!''}">
                    <button type="submit" class="btn  btn-info">提交</button>
                </form>
            </div>
        </div>
    </div>

</div>
</body>
</html>