package cn.pencilso.solitaire.common.model.wechat;

/**
 * @author pencilso
 * @date 2020/2/11 7:43 下午
 */
public class WechatGetUserInfoModel {

    /**
     * openId : oU0O25Ibsxq66dPB7BMMiRpH-32w
     * nickName : NotFoundMoneyException
     * gender : 1
     * language : zh_CN
     * city : Yichun
     * province : Jiangxi
     * country : China
     * avatarUrl : https://wx.qlogo.cn/mmopen/vi_32/sl6rK1Py9WVWmpiaTLvkhZ34okiaqtSbLn7ic4ouqJcakC3jkuYAQSqnZYlruEJBlUh4iaCykNEGm0KsibS2Rlican9w/132
     * watermark : {"timestamp":1581421335,"appid":"wxc3aa42a7978ebad6"}
     */

    private String openId;
    private String nickName;
    private int gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private WatermarkBean watermark;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public WatermarkBean getWatermark() {
        return watermark;
    }

    public void setWatermark(WatermarkBean watermark) {
        this.watermark = watermark;
    }

    public static class WatermarkBean {
        /**
         * timestamp : 1581421335
         * appid : wxc3aa42a7978ebad6
         */

        private int timestamp;
        private String appid;

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }
    }
}
