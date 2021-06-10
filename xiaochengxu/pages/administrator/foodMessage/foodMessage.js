// pages/administrator/addFood/addFood.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    show: false,
    selectData: [],
    leimuIndex: [],
    index: '',
    uploadImg: '',
    foodId: '',
    foodName: '',
    foodPrice: '',
    foodStock: '',
    foodDesc: ''
  },

  inputName(e) {
    this.setData({
      foodName: e.detail.value
    })
  },
  inputPrice(e) {
    this.setData({
      foodPrice: e.detail.value
    })
  },
  inputStock(e) {
    this.setData({
      foodStock: e.detail.value
    })
  },
  inputDescribe(e) {
    this.setData({
      foodDesc: e.detail.value
    })
  },
  selectTap() {
    this.setData({
      show: !this.data.show
    });
  },
  optionTap(e){
    let Index = e.currentTarget.dataset.index;
    this.setData({
      index: Index,
      show: !this.data.show
    });
  },
  selectImage() {
    var that = this;
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album'],
      success(res) {
        var tempFilePaths = res.tempFilePaths;
        var uploadImg = that.data.uploadImg;
        uploadImg = tempFilePaths[0];
        that.setData({
          uploadImg: uploadImg
        })
      }
    })
  },
  submit() {
    wx.request({
      url: 'http://localhost:8080/diancan/xcxfood/save',
      method: "POST",
      data: {
        foodId: this.data.foodId,
        foodName: this.data.foodName,
        foodStock: this.data.foodStock,
        foodPrice: this.data.foodPrice,
        foodDesc: this.data.foodDesc,
        foodIcon: this.data.uploadImg,
        leimuType: this.data.leimuIndex[this.data.index]
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
          url: '../dishManage/dishManage',
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: 'http://localhost:8080/diancan/xcxleimu/list',
      method: "GET",
      success(res) {
        var leimuList = that.data.selectData;
        var leimuIndexList = that.data.leimuIndex;
        for (var item in res.data) {
          if (res.data[item].leimuName && res.data[item].leimuId) {
            leimuList.push(res.data[item]);
            leimuIndexList.push(res.data[item].leimuType);
          }
        }
        that.setData({
          selectData: leimuList,
          leimuIndex: leimuIndexList
        })
      }
    })

    //缓存加载
    var food = wx.getStorageSync("food")
    console.log(food)
    this.setData({
      foodId: food.foodId,
      foodName: food.foodName,
      foodPrice: food.foodPrice,
      foodStock: food.foodStock,
      foodDesc: food.foodDesc,
      uploadImg: food.foodIcon,
      index: food.leimuType - 1
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