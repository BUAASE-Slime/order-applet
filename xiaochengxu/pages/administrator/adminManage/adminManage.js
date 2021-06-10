// pages/administrator/adminManage/adminManage.js
let app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    adminList: [],
    adminName: '',
    adminId: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: app.globalData.baseUrl + '/xcxadmin/list',
      method: "GET",
      success(res) {
        console.log(res.data);
        var adminList = that.data.adminList;
        for (var item in res.data) {
          if (res.data[item].adminId && res.data[item].username) {
            adminList.push(res.data[item]);
          }
        }
        that.setData({
          adminList: adminList
        })
      }
    })
  },
  back() {
    wx.redirectTo({
      url: '../../administrator/administrator',
    })
  },
  changeMessage(e) {
    var admin = e.currentTarget.dataset.admin;
    console.log(admin);
    wx.setStorageSync("admin", admin)
    wx.navigateTo({
      url: '../adminMessage/adminMessage'
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