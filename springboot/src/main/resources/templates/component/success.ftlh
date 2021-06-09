<html>
<#include "../component/head.ftlh">
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    操作成功了!
                </h4> <strong>${msg!""}</strong><a href="${url}" class="alert-link">3s后自动跳转</a>
            </div>
        </div>
    </div>
</div>

</body>

<script>
    setTimeout('location.href="${url}"', 3000);
</script>

</html>