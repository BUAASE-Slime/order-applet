let app = getApp();
var max_row_height = 5;
var food_row_height = 50;
var cart_offset = 90;
let categories = []
let tableNum = null;
let searchKey = null;

Page({
  data: {
    tabs: [""],
    activeIndex: 0,
    sliderOffset: 0,
    sliderLeft: 0,
    sliderWidth: 0.5,
    // 左右两侧菜单的初始显示次序
    curNav: 0,
    // 右菜单
    menu_list: [],
    // 左菜单
    foodList: [], //展示菜品
    // 购物车
    cartList: [],
    hasList: false, // 列表是否有数据
    totalPrice: 0, // 总价
    totalNum: 0, //总数

  },
  
 //搜索在这里
  getSearchKey(event) { //获取搜索词
    console.log("搜索词", event.detail.value)
    searchKey = event.detail.value
  },
  goSearch() { //去搜索页
    wx.navigateTo({
      url: '../buy/buy?searchKey=' + searchKey
    })
  },

  onLoad: function(options) {
    tableNum = wx.getStorageSync("tableNum")
    searchKey = options.searchKey;
    if (!searchKey) {
      searchKey = 'all'
    }
    console.log("传入桌号", tableNum)
    console.log("传入搜索词", searchKey)
    var that = this
    var arr = wx.getStorageSync('cart') || [];
    var menu_list = this.data.menu_list;
    var foodList = this.data.foodList;
    categories = []
    // 获取右侧菜品列表数据
    var resFood = []
    wx.request({
      url: app.globalData.baseUrl + '/buyerfoodList',
      data: {
        searchKey: searchKey
      },
      success(res) {
        if (res && res.data && res.data.data && res.data.data.length > 0) {
          let dataList = res.data.data;
          console.log(dataList)
          dataList.forEach((item, index) => { //遍历
            item.id = index;
            console.log(item);
            categories.push(item);
            if (index == 0) {
              resFood = item.foods; //默认选中第一项
            }
            item.foods.forEach((food, index) => {
              food.quantity = 0;
            });
          });
          that.setData({
            menu_list: categories,
            foodList: resFood,
          })
        } else {
          that.setData({
            list: []
          })
          wx.showLoading({
            title: '数据为空',
          })
        }
      }
    });

    // 购物车总量、总价
    var totalPrice = this.data.totalPrice
    var totalNum = this.data.totalNum
    console.log("存储购物车", arr)
    // 进入页面后判断购物车是否有数据，如果有，将菜单与购物车quantity数据统一
    if (arr.length > 0) {
      for (var i in arr) {
        for (var j in resFood) {
          if (resFood[j].id == arr[i].id) {
            resFood[j].quantity = arr[i].quantity;
          }
        }
      }
      // 进入页面计算购物车总价、总数
      for (var i in arr) {
        totalPrice += arr[i].price * arr[i].quantity;
        totalNum += Number(arr[i].quantity);
      }
    }

    // 赋值数据
    this.setData({
      hasList: true,
      cartList: arr,
      payFlag: this.data.payFlag,
      totalPrice: totalPrice.toFixed(2),//加小数点后两位
      totalNum: totalNum
    })

    wx.getSystemInfo({
      success: function(res) {
        that.setData({
          sliderLeft: (res.windowWidth / that.data.tabs.length - res.windowWidth / 2) / 2,
          sliderOffset: res.windowWidth / that.data.tabs.length * that.data.activeIndex,
        });//获得使用手机的尺寸信息
      }
    });
  },


  // 点击切换右侧数据
  changeRightMenu: function(e) {
    var classify = e.target.dataset.id; // 获取点击事件所在项的id
    var foodList = this.data.foodList;
    let foods = categories[classify].foods;
    this.setData({
      // 右侧菜单当前显示第curNav项
      curNav: classify,
      foodList: foods
    })

  },

  // 购物车增加数量
  addCount: function(e) {
    //点加号之前必须先扫码桌号
    if (!tableNum) {
      wx.showModal({
        title: '提示',
        content: '请到首页扫码识别桌号然后再来点餐',
        showCancel: false, //去掉取消按钮
        success: function(res) {
          if (res.confirm) {
            wx.switchTab({
              url: '../index/index',
            })//跳转到首页
          }
        }
      })
      return;
    }




    var id = e.currentTarget.dataset.id;
    var arr = wx.getStorageSync('cart') || [];
    var f = false;
    for (var i in this.data.foodList) { // 遍历菜单找到被点击的菜品，数量加1
      if (this.data.foodList[i].id == id) {
        this.data.foodList[i].quantity += 1;
        if (arr.length > 0) {
          for (var j in arr) { // 遍历购物车找到被点击的菜品，数量加1
            if (arr[j].id == id) {
              arr[j].quantity += 1;
              f = true;
              try {
                wx.setStorageSync('cart', arr)
              } catch (e) {
                console.log(e)
              }
              break;
            }
          }
          if (!f) {
            arr.push(this.data.foodList[i]);
          }
        } else {
          arr.push(this.data.foodList[i]);
        }
        try {
          wx.setStorageSync('cart', arr)
        } catch (e) {
          console.log(e)
        }
        break;
      }
    }

    this.setData({
      cartList: arr,
      foodList: this.data.foodList
    })
    this.getTotalPrice();
  },
  // 定义根据id删除数组的方法
  removeByValue: function(array, val) {
    for (var i = 0; i < array.length; i++) {
      if (array[i].id == val) {
        array.splice(i, 1);//删除数组
        break;
      }
    }
  },
  // 减少点菜数量
  minusCount: function(e) {
    var id = e.currentTarget.dataset.id;
    var arr = wx.getStorageSync('cart') || [];
    for (var i in this.data.foodList) {
      if (this.data.foodList[i].id == id) {
        this.data.foodList[i].quantity -= 1;
        if (this.data.foodList[i].quantity <= 0) {
          this.data.foodList[i].quantity = 0;
        }
        if (arr.length > 0) {
          for (var j in arr) {
            if (arr[j].id == id) {
              arr[j].quantity -= 1;
              if (arr[j].quantity <= 0) {
                this.removeByValue(arr, id)
              }
              if (arr.length <= 0) {
                this.setData({
                  foodList: this.data.foodList,
                  cartList: [],
                  totalNum: 0,
                  totalPrice: 0,
                })
                this.cascadeDismiss()
              }
              try {
                wx.setStorageSync('cart', arr)
              } catch (e) {
                console.log(e)
              }
            }
          }
        }
      }
    }
  //剪完了更新一下
    this.setData({
      cartList: arr,
      foodList: this.data.foodList
    })
    this.getTotalPrice();
  },
  // 获取购物车总价、总数
  getTotalPrice: function() {
    var cartList = this.data.cartList; // 获取购物车列表
    var totalP = 0;
    var totalN = 0
    for (var i in cartList) { // 循环列表得到每个数据
      totalP += cartList[i].quantity * cartList[i].price; // 所有价格加起来     
      totalN += cartList[i].quantity
    }
    this.setData({ // 最后赋值到data中渲染到页面
      cartList: cartList,
      totalNum: totalN,
      totalPrice: totalP.toFixed(2)
    });
  },
 


 
  
  // 跳转确认订单页面
  gotoOrder: function() {
    if (!tableNum) {
      wx.showModal({
        title: '提示',
        content: '请到首页扫码点餐',
        showCancel: false, //去掉取消按钮
        success: function(res) {
          if (res.confirm) {
            wx.switchTab({
              url: '../index/index',
            })
          }
        }
      })
      return;
    }
    //购物车为空
    var arr = wx.getStorageSync('cart') || [];
    console.log("arr", arr)
    if (!arr || arr.length == 0) {
      wx.showModal({
        title: '提示',
        content: '请选择菜品'
      })
      return;
    }

    let userInfo = app.globalData.userInfo;
    if (!userInfo || !userInfo.nickName) {
      wx.showModal({
        title: '请登录',
        content: '请到个人中心登录',
        showCancel: false, //去掉取消按钮
        success: function(res) {
          if (res.confirm) {
            wx.switchTab({
              url: '../my/my',
            })
          }
        }
      })
      return;
    }
    wx.navigateTo({
      url: '../confirm/confirm?tableNum=' + tableNum
    })
  },
  //根据此条搜索菜品
  GetQueryString: function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
  }


})