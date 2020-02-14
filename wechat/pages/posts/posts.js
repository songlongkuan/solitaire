var httputils = require('../../utils/httputils.js');

var pageNum = 1;
// var pageSize = 20;
var pageSize = 7;
//允许加载下一页
var onLoadNext = true;

var type = 1;
Page({
  data: {
    posts: [],
  },

  // 页面加载
  onLoad: function (options) {
    this.type = options.type;
    this.onPullDownRefresh()
  },

  // 下拉刷新
  onPullDownRefresh: function () {
    this.clearCache();
    this.queryPosts(1);//第一次加载数据
  },

  // 页面上拉触底事件（上拉加载更多）
  onReachBottom: function () {
    pageNum = pageNum + 1;
    this.queryPosts(pageNum);//后台获取新数据并追加渲染
  },

  // 清缓存
  clearCache: function () {
    pageNum = 1;
    onLoadNext = true;
    this.setData({
      posts: [] //文章列表数组清空
    });
  },
  queryPosts(page) {
    if (!onLoadNext) {
      return
    }

    var that = this;
    var url = "";
    if (this.type == 1) {
      url = "/api/client/post/query_myrelease";
    } else {
      url = "/api/client/post/query_myin";
    }
    httputils.requestGet(url, { pageNum: page, pageSize: pageSize }, function (res) {
      if (res.data.length == 0) {
        onLoadNext = false;
        wx.showToast({
          title: '没有数据了~',
          icon: 'none'
        })
        return;
      }
      var array = that.data.posts.concat(res.data);
      that.setData({
        posts: array
      })
    })
  },
  itemOnClick: function (e) {
    var bean = e.currentTarget.dataset.bean
    var url = '/pages/details/details?postMid=' + bean.postMid;
    wx.navigateTo({
      url: url
    })
  }
})
