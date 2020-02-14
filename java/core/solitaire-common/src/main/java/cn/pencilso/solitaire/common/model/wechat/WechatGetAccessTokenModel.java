package cn.pencilso.solitaire.common.model.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author pencilso
 * @date 2020/2/11 6:56 下午
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class WechatGetAccessTokenModel {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("errcode")
    private Integer errcode;

    @JsonProperty("errmsg")
    private String errmsg;

}
