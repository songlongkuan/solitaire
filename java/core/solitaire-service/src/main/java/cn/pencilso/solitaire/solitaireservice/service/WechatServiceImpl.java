package cn.pencilso.solitaire.solitaireservice.service;

import cn.pencilso.solitaire.common.config.SolitaireProperties;
import cn.pencilso.solitaire.common.model.wechat.WechatGetAccessTokenModel;
import cn.pencilso.solitaire.common.model.wechat.WechatLoginResponeModel;
import cn.pencilso.solitaire.common.plugin.HttpPlugin;
import cn.pencilso.solitaire.common.service.WechatService;
import cn.pencilso.solitaire.common.toolkit.Kv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/11 6:50 下午
 */
@Service
@Slf4j
@Validated
public class WechatServiceImpl implements WechatService {
    @Autowired
    private HttpPlugin httpPlugin;
    @Autowired
    private SolitaireProperties solitaireProperties;

    @Override
    public Optional<WechatLoginResponeModel> jscode2session(@NotBlank String code) {
        Optional<WechatLoginResponeModel> wechatLoginResponeModel = httpPlugin.get(
                "https://api.weixin.qq.com/sns/jscode2session",
                Kv.stringObjectKv().set("appid", solitaireProperties.getWechatAppkey())
                        .set("secret", solitaireProperties.getWechatAppsecret())
                        .set("js_code", code)
                        .set("grant_type", "authorization_code"),
                WechatLoginResponeModel.class
        );
        return wechatLoginResponeModel;
    }

    @Override
    public Optional<WechatGetAccessTokenModel> getAccessToken() {
        Optional<WechatGetAccessTokenModel> wechatGetAccessTokenModel = httpPlugin.get(
                "https://api.weixin.qq.com/cgi-bin/token",
                Kv.stringObjectKv().set("grant_type", "client_credential")
                        .set("appid", solitaireProperties.getWechatAppkey())
                        .set("secret", solitaireProperties.getWechatAppsecret()),
                WechatGetAccessTokenModel.class
        );
        return wechatGetAccessTokenModel;
    }
}
