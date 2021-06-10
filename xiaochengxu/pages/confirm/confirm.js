// tableNum: "",
//     confirmOrder: [],
//     diner_num: 0,
//     diner_numF: false,
let app = getApp();
let payWay = [{
  "id": 1,
  "package": ""
}, {
  "id": 2,
  "package": "微信支付"
}, {
  "id": 3,
  "package": ""
}]
// remarks: "",
// payWayList: [],
// cartList: [],
// totalPrice: 0,
// totalNum: 0,
// maskFlag: true,let app = getApp();
Page({
  //页面的初始数据
  data: {
    tableNum: "",
    confirmOrder: [],
    diner_num: 0,
    diner_numF: false,
    remarks: "",
    payWayList: [],
    cartList: [],
    totalPrice: 0,
    totalNum: 0,
    maskFlag: true,
  },
  onLoad: function(Options) {
    var that = this;
    let tableNum = Options.tableNum;
    var arr = wx.getStorageSync('cart') || [];
    for (var i in arr) {
      this.data.totalPrice += arr[i].quantity * arr[i].price;
      this.data.totalNum += arr[i].quantity
    }
    this.setData({
      tableNum: tableNum,
      cartList: arr,
      totalPrice: this.data.totalPrice.toFixed(2),
      totalNum: this.data.totalNum
    })
  },

  //打开支付方式弹窗
  choosePayWay: function() {
    var payWayList = this.data.payWayList
    var that = this;
    var rd_session = wx.getStorageSync('rd_session') || [];
    
      payWayList.push(payWay[1])
    
    console.log("来啦")
    console.log(payWayList);

  
  },
  
  // 获取备注信息
  getRemark: function(e) {
    var remarks = this.data.remarks;
    this.setData({
      remarks: e.detail.value
    })
  },
  //提交订单
  submitOrder: function(e) {
    let that = this;
    let tableNum = that.data.tableNum;

    //校验是否填写手机号
    let phone = app.globalData.userInfo.realphone
    console.log("手机号" + phone)
    if (phone == '' || phone == undefined) {
      wx.showModal({
        title: '提示',
        content: '请填写手机号',
        success: (res => {
          if (res.confirm) {
            wx.navigateTo({
              url: '../edit/edit',
            })
          }
        })
      })
      return
    }


    let arr = wx.getStorageSync('cart') || [];

    let goods_arr = [];
    arr.forEach(order => {
      console.log(order);
      var goods = new Object();
      goods.foodId = order.id;
      goods.foodQuantity = order.quantity;
      goods_arr.push(goods)
    })

    let goods_josn = JSON.stringify(goods_arr);
    console.log(goods_josn)
    let diner_num = this.data.diner_num; //用餐人数
    let dinerNum;
    let remarks = this.data.remarks; //备注信息
    let payId = e.currentTarget.dataset.id;
    let rd_session = wx.getStorageSync('rd_session') || [];
    if (diner_num == 0) {
      that.setData({
        diner_num: 1
      })
    }
    var peoples = this.data.diner_num
    console.log("备注：" + remarks)
    console.log("桌号" + tableNum)

    wx.request({
      url: app.globalData.baseUrl + '/userOrder/create',
      method: "POST",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        openid: app.globalData.openid,
        name: app.globalData.userInfo.nickName,
        phone: app.globalData.userInfo.realphone,
        address: tableNum,
        items: goods_josn//订单数据
      },
      success: function(res) {
        // var rescode = res.data.code
        console.log("支付成功", res.data)
        if (res && res.data && res.data.data) {
          wx.showToast({
            title: '下单成功！',
          })
          wx.setStorageSync('cart', "")
          wx.navigateTo({
            url: '../myOrder/myOrder'
          })
        } else {
          wx.showToast({
            title: '下单失败！',
          })
        }

      }
    })

  },


})