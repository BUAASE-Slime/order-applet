// pages/administrator/dishClassify/dishClassify.js
let app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    leimuList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: app.globalData.baseUrl + '/xcxleimu/list',
      method: "GET",
      success(res) {
        console.log(res.data);
        var leimuList = that.data.leimuList;
        for (var item in res.data) {
          if (res.data[item].leimuId && res.data[item].leimuName) {
            leimuList.push(res.data[item]);
          }
        }
        that.setData({
          leimuList: leimuList
        })
      }
    })
  },
  changeMessage(e) {
    var leimu = e.currentTarget.dataset.leimu;
    console.log(leimu);
    wx.setStorageSync("leimu", leimu)
    wx.navigateTo({
      url: '../leimuMessage/leimuMessage',
    })
  },
  removeLeimu(e) {
    var leimuId = e.currentTarget.dataset.id;
    console.log(leimuId);
    wx.showModal({
      title: '删除类目',
      content: '确定删除该类目?',  
      mask: true,     
      success(res) {
        if (res.cancel) {
          //点击取消,默认隐藏弹框
        } else {
          wx.request({
            url: 'http://localhost:8080/diancan/xcxleimu/remove',
            method: "GET",
            data: {
              leimuId: leimuId
            }
          })
          wx.navigateTo({
            url: '../dishClassify/dishClassify',
          })
        }
      }
    })
  },
  addLeimu() {
    wx.clearStorage({})
    wx.navigateTo({
      url: '../leimuMessage/leimuMessage',
    })
  },
  back() {
    wx.navigateTo({
      url: '../../administrator/administrator',
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