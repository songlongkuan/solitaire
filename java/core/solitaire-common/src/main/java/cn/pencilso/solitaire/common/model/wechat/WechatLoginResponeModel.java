package cn.pencilso.solitaire.common.model.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author pencilso
 * @date 2020/2/10 10:15 下午
 */
@Data
public class WechatLoginResponeModel {

    @JsonProperty("session_key")
    private String sessionKey;
    private String openid;

    private Integer errcode;
    private String errmsg;
}
