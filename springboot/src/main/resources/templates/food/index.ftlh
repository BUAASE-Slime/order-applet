<html>
<#include "../component/head.ftlh">

<body>
<div id="wrapper" class="toggled">
    <#--内容区域头部样式-->
    <h2 class="bg-info text-white p-3  text-center">添加单个菜品</h2>
    <#--左边栏-->
    <#include "../component/left.ftlh">
    <#--主要内容content-->
    <div id="page-content-wrapper" class="container">
        <div class="row clearfix">
            <div class="col-md-8 offset-2 column">
                <form role="form" method="post" action="/diancan/food/save">
                    <div class="form-group">
                        <label>菜品名(必填)</label>
                        <input name="foodName" type="text" class="form-control"
                               value="${(food.foodName)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>菜品价格(必填)</label>
                        <input name="foodPrice" type="text" class="form-control"
                               value="${(food.foodPrice)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>菜品库存(必填)</label>
                        <input name="foodStock" type="number" class="form-control"
                               value="${(food.foodStock)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>食物描述(非必填)</label>
                        <input name="foodDesc" type="text" class="form-control"
                               value="${(food.foodDesc)!''}"/>
                    </div>

                    <div class="form-group">
                        <label>所属类目</label>
                        <select name="leimuType" class="form-control">
                            <#list leimuList as leimu>
                                <option value="${leimu.leimuType}"
                                        <#if (food.foodType)?? && food.foodType == leimu.leimuType>
                                            selected
                                        </#if>
                                >${leimu.leimuName}
                                </option>
                            </#list>
                        </select>
                    </div>
                    <input hidden type="text" name="foodId" value="${(food.foodId)!''}">
                    <div class="form-group">
                        <label>图片(必填)</label>
                        <img height="100" width="100" src="${(food.foodIcon)!''}" alt="">
                        <input name="foodIcon" type="text" class="form-control"
                               value="${(food.foodIcon)!''}"/>
                    </div>
                    <button type="submit" class="btn  btn-info">提交</button>
                </form>
            </div>
        </div>
    </div>
</div>

</div>
</body>
</html>