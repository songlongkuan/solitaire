package cn.pencilso.solitaire.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author pencilso
 * @date 2020/2/10 10:01 下午
 */
@Data
@ConfigurationProperties(prefix = "solitaire")
public class SolitaireProperties {

    private String wechatAppkey;

    private String wechatAppsecret;


    /**
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/access-token/auth.getAccessToken.html
     * 微信授权令牌
     */
    private volatile String wechatAccesstoken;
}
