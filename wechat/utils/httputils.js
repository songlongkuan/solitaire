function requestPost(url, data, success) {
  request(url, "POST", 'application/x-www-form-urlencoded', data, success)
}
function requestGet(url, data, success) {
  request(url, "GET", '', data, success)
}


function request(url, method, contentType, data, success) {
  var accesstoken = wx.getStorageSync('accesstoken');
  wx.showLoading({
    icon: 'loading'
  })
  var doman = "https://solitaire.pencilso.cn";
  wx.request({
    url: doman + url, //仅为示例，并非真实的接口地址
    data: data,
    method: method,
    header: {
      // 'content-type': 'application/json', // 默认值
      'Content-type': contentType,
      "accesstoken": accesstoken
    }, success(res) {
      if (res.data.code == 0) {
        success(res.data)
      } else {
        if (res.code > 13000) {
          wx.redirectTo({
            url: '/pages/login/login'
          })
          return;
        }
        wx.showToast({
          icon: 'none',
          title: res.data.message,
          duration: 2000,
          success() {

          }
        })
      }
    }, fail(res) {

    }, complete(res) {
      wx.hideLoading()
    }
  })
}


module.exports = {
  requestPost: requestPost,
  requestGet: requestGet
}