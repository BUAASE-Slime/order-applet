// pages/administrator/pictureMessage/pictureMessage.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    picId: '',
    picUrl: '',
    picMessage: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //缓存加载
    var picture = wx.getStorageSync("picture")
    console.log(picture)
    this.setData({
      picId: picture.picId,
      picUrl: picture.picUrl,
      picMessage: picture.picMessage
    })
  },
  inputUrl(e) {
    this.setData({
      picUrl: e.detail.value
    })
  },
  inputMessage(e) {
    this.setData({
      picMessage: e.detail.value
    })
  },
  submit() {
    wx.request({
      url: 'http://localhost:8080/diancan/xcxpicture/save',
      method: "POST",
      data: {
        picId: this.data.picId,
        picUrl: this.data.picUrl,
        picMessage: this.data.picMessage
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
          url: '../pictureManage/pictureManage',
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