// pages/adminMessage/adminMessage.js
let app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    adminId: '',
    username: '',
    phone: '',
    password: '',
    adminType: '',
    show: false,
    selectData:['员工','管理员'],
    index: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //缓存加载
    var admin = wx.getStorageSync("admin")
    console.log(admin)
    this.setData({
      adminId: admin.adminId,
      username: admin.username,
      phone: admin.phone,
      password: admin.password,
      index: admin.adminType - 1
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
  inputName(e) {
    this.setData({
      username: e.detail.value
    })
  },
  inputPhone(e) {
    this.setData({
      phone: e.detail.value
    })
  },
  inputPassword(e) {
    this.setData({
      password: e.detail.value
    })
  },
  submit() {
    wx.request({
      url: app.globalData.baseUrl + '/xcxadmin/save',
      method: "POST",
      data: {
        username: this.data.username,
        password: this.data.password,
        phone: this.data.phone,
        adminId: this.data.adminId,
        adminType: this.data.index + 1
      },
      success(res) {
        console.log(res);
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
          url: '../adminManage/adminManage',
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