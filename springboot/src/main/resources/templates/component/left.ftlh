<style>
    * {
        margin: 0;
        padding: 0;
    }

    a:hover, a:focus {
        text-decoration: none
    }


    .mobile-nav ul li a {
        color: white;
        font-size: 1.2em;
        display: block;
        padding: 1em 15%;
        border-bottom: 1px solid #6E8B3D;
        transition: all 0.2s ease-out;
        cursor: pointer;
    }

    .mobile-nav {
        position: fixed;
        top: 0;
        left: 0;
        width: 180px;
        height: 100%;
        background-color: #70C570;
        z-index: 9999;
        transition: all 0.3s ease-in;
    }

    .mobile-nav ul li a:hover {
        background-color: #17A2B8;
        color: #ffffff;
    }

</style>

<div id="mobile-menu" class="mobile-nav ">
    <ul id="leftRoot">
        <li><a href="/diancan/home/homeList">统计信息</a></li>
        <li><a href="/diancan/admin/list">餐厅管理员</a></li>
        <li><a href="/diancan/picture/list">轮播图管理</a></li>
        <li><a href="/diancan/leimu/list">菜品类目管理</a></li>
        <li><a href="/diancan/food/list">菜品管理</a></li>
        <li><a href="/diancan/adimOrder/list">菜品订单</a></li>
        <li><a href="/diancan/adminPaihao/list">排号管理</a></li>
        <li><a href="/diancan/logoutAdmin">退出登陆</a></li>
    </ul>
</div>


