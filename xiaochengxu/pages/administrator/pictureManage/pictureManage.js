// pages/administrator/pictureManage/pictureManage.js
let app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pictures: [],

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: app.globalData.baseUrl + '/xcxpicture/list',
      method: "GET",
      success(res) {
        console.log(res.data);
        var pictures = that.data.pictures;
        for (var item in res.data) {
          if (res.data[item].picId && res.data[item].picUrl) {
            pictures.push(res.data[item]);
          }
        }
        that.setData({
          pictures: pictures
        })
      }
    })
  },
  back() {
    wx.redirectTo({
      url: '../../administrator/administrator',
    })
  },
  removePicture(e) {
    var picId = e.currentTarget.dataset.id;
    console.log(picId);
    wx.showModal({
      title: '删除图片',
      content: '确定删除该轮播图?',  
      mask: true,     
      success(res) {
        if (res.cancel) {
          //点击取消,默认隐藏弹框
        } else {
          wx.request({
            url: app.globalData.baseUrl + '/xcxpicture/remove',
            method: "GET",
            data: {
              picId: picId
            }
          })
          wx.redirectTo({
            url: '../pictureManage/pictureManage',
          })
        }
      }
    })
  },
  addPicture() {
    wx.clearStorage({})
    wx.navigateTo({
      url: '../pictureMessage/pictureMessage',
    })
  },
  changeMessage(e) {
    var picture = e.currentTarget.dataset.picture;
    console.log(picture);
    wx.setStorageSync("picture", picture)
    wx.navigateTo({
      url: '../pictureMessage/pictureMessage',
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