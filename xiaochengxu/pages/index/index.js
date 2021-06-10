//index.js
const app = getApp()
let searchKey = null
Page({
  data: {
    banner: [],//用来放轮播图的数组
  },
  onLoad() {
    this.getTopBanner(); //请求顶部轮播图
  },
  //页面可见
  onShow() {
    searchKey = '' //每次返回首页时，清空搜索词
  },
  //扫码点餐
  btnclick1: function() {
    let that = this;

    //注意：true 扫码点餐； false 本地点餐
    let saoMa = true;

    /**
     * 本地点餐
     */
    if (!saoMa) {
      that.goToBuy("1号桌")
      return
    }

    /**
     * 扫码点餐
     */
    wx.scanCode({
      success: (res) => {
        if (res.result) {
          let str = res.result;
          if (str.search("1") != -1) {
            that.goToBuy("1号桌")
          } else if (str.search("2") != -1) {
            that.goToBuy("2号桌")
          } else if (str.search("3") != -1) {
            that.goToBuy("3号桌")
          } else {
            console.log("桌号错误")
          }
        }
      }
    })
  },

  //去点餐页
  goToBuy(tableNum) {
    wx.setStorageSync("tableNum", tableNum)
    //把菜品搜索词条保存起来
    wx.navigateTo({
      url: '../Food/food'
    })
  },

  //菜品浏览
  btnclick2: function() {
    wx.navigateTo({
      url: '../Food/food'
    })
  },

  btnclick4: function() {
    console.log("跳转到后台小程序啦！")
    wx.navigateTo({
<<<<<<< HEAD
      url: '/pages/adminLogin/adminLogin',
=======
      url: '',
>>>>>>> origin/hzh
    })
  } 
  ,
  //获取首页顶部轮播图
  getTopBanner() {
    let that = this;
    wx.request({
      url: app.globalData.baseUrl + '/wxPicture/getAll',
      success: function(res) {
        console.log("请求到的轮播图", res)
        if (res && res.data && res.data.data && res.data.data.length > 0) {
          let dataList = res.data.data;
          console.log("请求到的轮播图", dataList)
          that.setData({
            banner: dataList
          })
        } else {
          that.setData({
            list: []
          })
        }
      }
    })
  }


})