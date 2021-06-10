// pages/adminLogin/adminLogin.js
let app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    username: '',
    password: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  login() {
    wx.request({
      url: app.globalData.baseUrl + '/xcxlogin',
      method: "POST",
      /** 传多个值，需要改变请求头 */
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: {
        password: this.data.password,
        username: this.data.username
      },
      success(res) {
        if (res.data == 1) {
          console.log("登录成功");
          setTimeout(() => {
            wx.showToast({
              title: '登录成功',
              mask: true,
              icon: 'success',
            });
            setTimeout(() => {
              wx.hideToast();
            }, 2000)
          }, 0)
          wx.navigateTo({
            url: '../administrator/administrator',
          })
        } else {
          console.log("登录失败，请检查用户名和密码");
          setTimeout(() => {
            wx.showToast({
              title: '登录失败',
              mask: true,
              image: '../../image/失败.png',
            });
            setTimeout(() => {
              wx.hideToast();
            }, 2000)
          }, 0)
        }
      }
    })
  },
  inputName(e) {
    this.setData({
      username: e.detail.value
    })
  },
  inputPassword(e) {
    this.setData({
      password: e.detail.value
    })
  },


  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})