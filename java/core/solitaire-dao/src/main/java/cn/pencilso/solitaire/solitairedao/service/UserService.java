package cn.pencilso.solitaire.solitairedao.service;

import cn.pencilso.solitaire.solitairedao.model.UserModel;
import cn.pencilso.solitaire.solitairedao.base.BaseIService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-11
 */
public interface UserService extends BaseIService<UserModel> {

    /**
     * 根据openid 查询用户模型
     * @param openId
     * @return
     */
    Optional<UserModel> getByWechatOpenId(@NotBlank String openId);

    /**
     * 根据用户ID 获取 jwtsalt
     *
     * @param uMid
     * @return
     */
    Optional<String> getJwtSalt(@NotNull Long uMid);




}
