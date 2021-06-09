<html>
<#include "../component/head.ftlh">
<body style=" background: url(/diancan/img/bg.png)
no-repeat center center ; background-size: cover;">
<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title text-center" id="myModalLabel">登录</h4>
        </div>
        <div class="modal-body" id="model-body">
            <div class="form-group">
                <input type="text" name="phoneOrname" class="form-control" placeholder="用户名/手机号">
            </div>
            <div class="form-group">

                <input type="password" name="password" class="form-control" placeholder="密码" autocomplete="off">
            </div>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" id="loginBtn" class="btn btn-primary form-control">登录</button>
            </div>
        </div>
    </div>
</div>

<script>
    /*
    * 登陆页
    * */
    $("#loginBtn").click(function () {
        console.log("点击了登录2")
        login();
    });

    function login() {
        let phoneOrname = $("input[ name='phoneOrname' ] ").val();
        let password = $("input[ name='password' ] ").val();
        if (phoneOrname == undefined || phoneOrname == "") {
            alert("请输入手机号/用户名");
            return;
        }
        if (password == undefined || password == "") {
            alert("请输入密码");
            return;
        }
        $.ajax({
            url: "/diancan/loginAdmin",
            data: {
                "phoneOrname": phoneOrname,
                "password": password
            },
            success: function (data) {
                console.log("登录成功", data)
                $("#mainContainer").html(data);
                window.location.href = "/diancan/home/homeList";
            },
            error: function (err) {
                console.log("登录失败", err)
                alert("登录失败,账号或者密码不正确");
            }
        });
    }
</script>
</body>
</html>