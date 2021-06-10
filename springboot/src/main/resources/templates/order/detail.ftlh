<html>
<#include "../component/head.ftlh">

<body>
<div id="wrapper" class="toggled">
    <#--内容区域头部样式-->
    <h2 class="bg-info text-white p-3  text-center">菜品订单详情页</h2>
    <#--左边栏-->
    <#include "../component/left.ftlh">
    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <#--订单详情表数据-->
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr class="table-info">
                            <th>订单id</th>
                            <#--                            <th>订单总金额</th>-->
                            <th>商品id</th>
                            <th>商品名称</th>
                            <th>价格</th>
                            <th>数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTO.orderDetailList as orderDetail>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <#--                                <td>${orderDTO.orderAmount}</td>-->
                                <td>${orderDetail.foodId}</td>
                                <td>${orderDetail.foodName}</td>
                                <td>${orderDetail.foodPrice}</td>
                                <td>${orderDetail.foodQuantity}</td>
                                <td>${orderDetail.foodQuantity * orderDetail.foodPrice}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <#--操作-->
                <div class="col-md-12 column">
                    <#if orderDTO.getOrderStatus() == 0||orderDTO.getOrderStatus()== 1>
                        <a href="/diancan/adimOrder/finish?orderId=${orderDTO.orderId}" type="button"
                           class="btn  btn-info btn-primary">订单完成</a>
                        <a href="/diancan/adimOrder/cancel?orderId=${orderDTO.orderId}" type="button"
                           class="btn  btn-info btn-danger">取消订单</a>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>