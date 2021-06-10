// pages/administrator/leimuMessage/leimuMessage.js
let app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    leimuId: '',
    leimuName: '',
    leimuType: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //缓存加载
    var leimu = wx.getStorageSync("leimu")
    console.log(leimu)
    this.setData({
      leimuId: leimu.leimuId,
      leimuName: leimu.leimuName,
      leimuType: leimu.leimuType
    })
  },
  inputName(e) {
    this.setData({
      leimuName: e.detail.value
    })
  },
  inputType(e) {
    this.setData({
      leimuType: e.detail.value
    })
  },
  submit() {
    wx.request({
      url: app.globalData.baseUrl + '/xcxleimu/save',
      method: "POST",
      data: {
        leimuId: this.data.leimuId,
        leimuName: this.data.leimuName,
        leimuType: this.data.leimuType
      },
      success(res) {
        console.log(res.data);
        setTimeout(() => {
          wx.showToast({
            title: '提交成功',
            mask: true,
            icon: 'success',
          });
          setTimeout(() => {
            wx.hideToast();
          }, 2000)
        }, 0)
        wx.navigateTo({
          url: '../dishClassify/dishClassify',
        })
      }
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