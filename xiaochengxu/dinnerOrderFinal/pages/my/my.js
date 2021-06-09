const app = getApp();
Page({
  data: {
    isShowUserName: false,
    userInfo: null,
  },
  onGotUserInfo: function (e) {
    if (e.detail.userInfo) {
      var user = e.detail.userInfo;
      this.setData({
        isShowUserName: true,
        userInfo: e.detail.userInfo,
      })
      user.openid = app.globalData.openid;
      app._saveUserInfo(user);
      //这些函数都在app.js里面
    } else {
      app._showSettingToast('登陆需要获取您的授权');
    }
  },

  goToMyOrder: function () {
    wx.navigateTo({
      url: '../myOrder/myOrder',
    })
  },
  goToPhone() {
    wx.makePhoneCall({
      phoneNumber: '18622331560' //这是示范例子，号码是我的手机号18622331560
    })
  },
  change() {
    wx.navigateTo({
      url: '../edit/edit',
    })
  },
  onShow(options) {
    var user = app.globalData.userInfo;
    if (user && user.nickName) {
      this.setData({
        isShowUserName: true,
        userInfo: user,
      })
    }
  },

  //生命周期函数，进行初始赋值加载
  onLoad: function (options) {
    var that = this;
    var user = app.globalData.userInfo;
  
  },
})