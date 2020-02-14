const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}
function closewindow() {
  //返回上一级关闭当前页面
  wx.navigateBack({
    delta: 1
  })
}

function getAccesstoken() {
  var accesstoken = wx.getStorageSync('accesstoken');
  return accesstoken;
}

function setAccesstoken(accesstoken) {
  wx.setStorageSync('accesstoken', accesstoken)
}

function checkLogin() {
  var taccesstoken = this.getAccesstoken();
  if (taccesstoken != '') {
    return true;
  } else {
    wx.navigateTo({
      url: '/pages/login/login'
    })
    return false;
  }
}


module.exports = {
  formatTime: formatTime,
  closewindow: closewindow,
  getAccesstoken: getAccesstoken,
  setAccesstoken: setAccesstoken,
  checkLogin: checkLogin
}
