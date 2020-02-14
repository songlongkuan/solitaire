const app = getApp();
var util = require('../../utils/util.js');

Page({
  onLoad: function () {
    // var accesstoken = wx.getStorageSync('accesstoken');
    // if (accesstoken != '') {

    // } else {
    //   wx.navigateTo({
    //     url: '/pages/login/login'
    //   })
    // }
  }, buttonrelease: function () {
    if (util.checkLogin()) {
      wx.navigateTo({
        url: '/pages/release/release'
      })
    }
  }, mypost: function () {
    if (util.checkLogin()) {
      wx.navigateTo({
        url: '/pages/posts/posts?type=1'
      })
    }
  }, inpost: function () {
    if (util.checkLogin()) {
      wx.navigateTo({
        url: '/pages/posts/posts?type=2'
      })
    }
  }, help: function () {
    wx.showToast({
      title: '暂无',
      icon: 'none'
    })
  }
})