package cn.pencilso.solitaire.common.service;

import cn.pencilso.solitaire.common.model.wechat.WechatGetAccessTokenModel;
import cn.pencilso.solitaire.common.model.wechat.WechatLoginResponeModel;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

/**
 * 微信接口服务
 *
 * @author pencilso
 * @date 2020/2/11 6:37 下午
 */
public interface WechatService {

    /**
     * jscode to session
     *
     * @param code
     * @return
     */
    Optional<WechatLoginResponeModel> jscode2session(@NotBlank String code);

    /**
     * 获取小程序全局唯一后台接口调用凭据（access_token）。
     * 调用绝大多数后台接口时都需使用 access_token，开发者需要进行妥善保存。
     * 每次获取的token 都会导致之前的token失效
     *
     * @return
     */
    Optional<WechatGetAccessTokenModel> getAccessToken();
}
