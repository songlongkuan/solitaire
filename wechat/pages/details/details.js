const app = getApp();
var util = require('../../utils/util.js');
var httputils = require('../../utils/httputils.js');


var postMid = "";
var pageNum = 1;
var pageSize = 20;
//允许加载下一页
var onLoadNext = true;
Page({
  data: {
    //接龙详情
    details: {},
    //接龙数据
    postin: [],
    subbuttontitle: "接龙"
  },
  onLoad: function (options) {
    postMid = options.postMid;
    var that = this;
    httputils.requestGet("/api/client/post/query_postdetail/" + postMid, {}, function (result) {
      that.setData({
        details: result.data
      })
      that.queryPostinlist(1);
    });

  },
  queryPostinlist: function (pageNum) {
    var that = this;
    httputils.requestGet("/api/client/post/query_postinlist/" + postMid, { pageNum: pageNum, pageSize: pageSize }, function (res) {
      if (res.data.length == 0) {
        onLoadNext = false;
        return;
      }
      that.pageNum = pageNum + 1;
      var array = that.data.postin.concat(res.data);
      that.setData({
        postin: array
      })
      if (res.postInContent != "") {
          that.setData({
            subbuttontitle:"修改"
          })
      }
    })
  },
  onReachBottom: function () {
    if (!this.onLoadNext) {
      wx.showToast({
        title: '没有更多数据了~',
        icon: 'none'
      })
    } else {
      this.queryPostinlist(pageNum);
    }
  },
  submitPostIn: function (e) {
    var that = this;
    var value = e.detail.value;
    var postin_content = value.postin_content;
    if (postin_content == '') {
      postin_content = "+1";
    }

    httputils.requestPost("/api/client/post/post_in", { postMid: postMid, content: postin_content }, function (result) {
      wx.showToast({
        title: result.message,
        icon: 'none'
      })
      that.refresh();
    })
  },
  refresh: function () {
    this.setData({
      postin: []
    })
    this.queryPostinlist(1);
  },
  showActionSheet: function () {
    var that = this;
    wx.showActionSheet({
      itemList: ["截止", "导出到粘贴板"],
      success(e) {
        if (e.camcle) {
          return
        }
        var tapIndex = e.tapIndex;
        switch (tapIndex) {
          case 0:
            that.closePost()
            break
          case 1:
            httputils.requestGet("/api/client/post/post_export/" + postMid, {}, function (res) {
              wx.setClipboardData({
                data: res.data,
                success(res) {
                  wx.showToast({
                    title: '复制成功',
                  })
                }
              })
            })
            break
        }
      },
      fail(e) {

      },
      complete(e) {

      }
    })
  },
  //截止接龙
  closePost: function () {
    var that = this;
    httputils.requestGet("/api/client/post/close_post/" + postMid, {}, function (res) {
      wx.showToast({
        title: res.message,
        icon: 'none'
      })
      httputils.requestGet("/api/client/post/query_postdetail/" + postMid, {}, function (result) {
        that.setData({
          details: result.data
        })
      });
    })
  }
})