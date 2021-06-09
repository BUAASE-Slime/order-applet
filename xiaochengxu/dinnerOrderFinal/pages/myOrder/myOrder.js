var app = getApp()
let orderStatus = 1; //0"新订单，未支付;1"新订单，已支付";4“已完成”
Page({
  data: {
    // 顶部菜单切换
    navbar: ["待上餐",  "已完成"],
    // 默认选中菜单
    currentTab: 0,
    isShowComment: false, 
    list: []
  },
  //顶部tab切换
  navbarTap: function(e) {
    let index = e.currentTarget.dataset.idx;
    this.setData({
      currentTab: index
    })

    //1"新订单，已支付";2, "已取消"；3"已完成"；
    if (index == 0) {
      orderStatus = 1;
    }  else {
      orderStatus = 3;
    }
    this.getMyOrderList();
  },

  onShow: function() {
    this.getMyOrderList();
  },

  getMyOrderList() {
    let that = this;
    let openid = app._checkOpenid();
    if (!openid) {
      return;
    }
    //请求自己后台获取用户openid
    wx.request({
      url: app.globalData.baseUrl + '/userOrder/listByStatus',
      data: {
        openid: openid,
        orderStatus: orderStatus
      },
      success: function(res) {
        console.log(res);
        if (res && res.data && res.data.data && res.data.data.length > 0) {
          let dataList = res.data.data;
          console.log(dataList)
          that.setData({
            list: dataList
          })
        } else {
          that.setData({
            list: []
          })
        }
      }
    })
  },
 
  
})