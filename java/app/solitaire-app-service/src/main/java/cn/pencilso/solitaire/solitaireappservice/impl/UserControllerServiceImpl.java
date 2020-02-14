package cn.pencilso.solitaire.solitaireappservice.impl;

import cn.pencilso.solitaire.common.config.SolitaireProperties;
import cn.pencilso.solitaire.common.exception.SolitaireMessageException;
import cn.pencilso.solitaire.common.model.wechat.WechatGetUserInfoModel;
import cn.pencilso.solitaire.common.model.wechat.WechatLoginResponeModel;
import cn.pencilso.solitaire.common.plugin.JwtPlugin;
import cn.pencilso.solitaire.common.service.FileStoreService;
import cn.pencilso.solitaire.common.service.WechatService;
import cn.pencilso.solitaire.common.toolkit.JsonUtils;
import cn.pencilso.solitaire.common.toolkit.WechatUtil;
import cn.pencilso.solitaire.common.toolkit.id.IdGeneratorCore;
import cn.pencilso.solitaire.solitaireappservice.UserControllerService;
import cn.pencilso.solitaire.solitairedao.model.UserModel;
import cn.pencilso.solitaire.solitairedao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/10 8:09 下午
 */
@Slf4j
@Service
public class UserControllerServiceImpl implements UserControllerService {

    @Autowired
    private WechatService wechatService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtPlugin jwtPlugin;
    @Autowired
    private SolitaireProperties solitaireProperties;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private CacheManager cacheManager;

    @Transactional
    @Override
    public String wechatLogin(@NotNull String wechatCode, @NotBlank String wechatEncryptedData, @NotBlank String wechatVi) {
        WechatLoginResponeModel wechatLoginResponeModel = wechatService.jscode2session(wechatCode).orElseThrow(() -> new SolitaireMessageException("获取微信授权信息失败，请尝试稍后重试！"));
        //解密数据
        String decryptData = WechatUtil.decryptData(wechatEncryptedData, wechatLoginResponeModel.getSessionKey(), wechatVi).orElseThrow(() -> new SolitaireMessageException("解析微信数据失败，请尝试稍后重试！"));
        WechatGetUserInfoModel wechatGetUserInfoModel = null;
        try {
            wechatGetUserInfoModel = JsonUtils.jsonToObject(decryptData, WechatGetUserInfoModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SolitaireMessageException("解析用户信息数据失败，请尝试稍后重试！");
        }
        //检查该用户是否存在
        Optional<UserModel> dbUserModel = userService.getByWechatOpenId(wechatGetUserInfoModel.getOpenId());
        Long uMid;
        if (dbUserModel.isPresent()) {
            //用户存在
            uMid = dbUserModel.get().getMid();
        } else {
            //用户不存在 新增用户
            UserModel userModel = createUserByWechat(wechatGetUserInfoModel);
            uMid = userModel.getMid();
        }

        //生成一个新的令牌
        return updateUserLoginToken(uMid);
    }


    /**
     * 根据微信信息 创建一个新的用户
     *
     * @param wechatGetUserInfoModel
     */
    private UserModel createUserByWechat(WechatGetUserInfoModel wechatGetUserInfoModel) {
        if (!wechatGetUserInfoModel.getWatermark().getAppid().equals(solitaireProperties.getWechatAppkey()))
            throw new SolitaireMessageException("警告！ 未知数据异常！");

        UserModel userModel = new UserModel();
        if (!StringUtils.isEmpty(wechatGetUserInfoModel.getAvatarUrl())) {
            //上传文件到oss
            String filename = IdGeneratorCore.generatorUUID() + ".png";
            fileStoreService.putFile("avatar/", filename, wechatGetUserInfoModel.getAvatarUrl());
            userModel.setWechatAvatar(filename);
        }
        userModel.setWechatOpenid(wechatGetUserInfoModel.getOpenId());
        userModel.setNikeName(wechatGetUserInfoModel.getNickName());
        userModel.setWechatGender(wechatGetUserInfoModel.getGender());
        userModel.setWechatCity(wechatGetUserInfoModel.getCity());
        userModel.setWechatProvince(wechatGetUserInfoModel.getProvince());
        userModel.setMid(IdGeneratorCore.generatorId());
        if (!userService.save(userModel)) {
            throw new SolitaireMessageException("新增用户信息失败，请尝试稍后重试！");
        }
        return userModel;
    }

    /**
     * 更新用户的登录令牌
     *
     * @param uMid
     * @return
     */
    public String updateUserLoginToken(Long uMid) {
        String salt = IdGeneratorCore.generatorUUID();
        String accesstoken = jwtPlugin.generateToken(uMid, salt).orElseThrow(() -> new SolitaireMessageException("生成登录令牌失败，请尝试稍后重试！"));
        UserModel updateUserModel = new UserModel();
        updateUserModel.setJwtAccesstoken(accesstoken);
        updateUserModel.setJwtSalt(salt);
        if (!userService.updateModelByMid(updateUserModel, uMid)) {
            throw new SolitaireMessageException("更新登录令牌失败，请尝试稍后重试！");
        }

        Cache jwtsaltCache = cacheManager.getCache("jwtsalt");
        jwtsaltCache.put(uMid, Optional.of(salt));
        return accesstoken;
    }
}
