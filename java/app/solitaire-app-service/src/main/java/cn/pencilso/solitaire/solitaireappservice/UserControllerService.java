package cn.pencilso.solitaire.solitaireappservice;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * user 控制器对应的service
 *
 * @author pencilso
 * @date 2020/2/10 8:08 下午
 */
public interface UserControllerService {
    /**
     * 微信用户登录
     *
     * @param wechatCode          授权code
     * @param wechatEncryptedData 加密后的密文
     * @param wechatVi            微信授权用户信息返回的字段
     * @return
     */
    String wechatLogin(@NotNull String wechatCode, @NotBlank String wechatEncryptedData, @NotBlank String wechatVi);


}