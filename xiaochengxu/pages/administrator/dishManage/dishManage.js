// pages/dishManage/dishManage.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    foodList: [],
    foodName: '',
    foodPrice: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: 'http://localhost:8080/diancan/xcxfood/list',
      method: "GET",
      success(res) {
        console.log(res.data);
        var foodList = that.data.foodList;
        for (var item in res.data) {
          if (res.data[item].foodId && res.data[item].foodName && res.data[item].foodPrice) {
            foodList.push(res.data[item]);
          }
        }
        that.setData({
          foodList: foodList
        })
      }
    })
  },

  changeMessage: function(e) {
    var food = e.currentTarget.dataset.food;
    console.log(food);
    wx.setStorageSync("food", food)
    wx.navigateTo({
      url: '../foodMessage/foodMessage'
    })
  },
  addFood() {
    wx.clearStorage({})
    wx.navigateTo({
      url: '../foodMessage/foodMessage'
    })
  },
  removeFood(e) {
    var foodId = e.currentTarget.dataset.id;
    console.log(foodId);
    wx.showModal({
      title: '删除菜品',
      content: '确定删除该菜品?',  
      mask: true,     
      success(res) {
        if (res.cancel) {
          //点击取消,默认隐藏弹框
        } else {
          wx.request({
            url: 'http://localhost:8080/diancan/xcxfood/remove',
            method: "GET",
            data: {
              foodId: foodId
            }
          })
          wx.navigateTo({
            url: '../dishManage/dishManage',
          })
        }
      }
    })
  },
  onSale(e) {
    var foodId = e.currentTarget.dataset.id;
    wx.request({
      url: 'http://localhost:8080/diancan/xcxfood/on_sale',
      method: "GET",
      data: {
        foodId: foodId
      }
    })
    wx.navigateTo({
      url: '../dishManage/dishManage'
    })
  },
  offSale(e) {
    var foodId = e.currentTarget.dataset.id;
    wx.request({
      url: 'http://localhost:8080/diancan/xcxfood/off_sale',
      method: "GET",
      data: {
        foodId: foodId
      }
    })
    wx.navigateTo({
      url: '../dishManage/dishManage'
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