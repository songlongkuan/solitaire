var httputils = require('../../utils/httputils.js');
var util = require('../../utils/util.js');
Page({
  onLoad: function () {
      
  }, getUserInfo: function (code) {
    wx.getUserInfo({
      success: (res) => {
        httputils.requestPost(
          "/api/client/user/wechatlogin",
          { wechatCode: code, wechatEncryptedData: res.encryptedData, wechatVi: res.iv },
          function (result) {
            util.setAccesstoken(result.data.accesstoken);
            util.closewindow();
          }
        )
      },
    })
  }, userlogin() {
    wx.login({
      success: (res) => {
        this.getUserInfo(res.code)
      },
    })
  }
})