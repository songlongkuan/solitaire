package cn.pencilso.solitaire.solitairedao.service.impl;

import cn.pencilso.solitaire.common.config.AliyunOSSProperties;
import cn.pencilso.solitaire.solitairedao.mapper.UserMapper;
import cn.pencilso.solitaire.solitairedao.model.UserModel;
import cn.pencilso.solitaire.solitairedao.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-11
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements UserService {
    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;

    private String RESOURCE_PATH_AVATAR = null;

    @PostConstruct
    public void initServier() {
        RESOURCE_PATH_AVATAR = aliyunOSSProperties.getOssBinddoman() + "avatar/";
    }


    @Override
    public Optional<UserModel> getByWechatOpenId(@NotBlank String openId) {
        return Optional.ofNullable(lambdaQuery().eq(UserModel::getWechatOpenid, openId).one());
    }


    @Cacheable(value = "jwtsalt", key = "#uMid")
    @Override
    public Optional<String> getJwtSalt(@NotNull Long uMid) {
        try {
            Optional<UserModel> userModel = getByMid(uMid);
            if (userModel.isPresent()) {
                return Optional.of(userModel.get().getJwtSalt());
            }
        } catch (Exception ignored) {
            log.warn("getJwtSalt fail uMid: [{}]", uMid, ignored);
        }
        return Optional.empty();
    }


    @Override
    public List<UserModel> buildResourcePath(List<UserModel> modelList) {
        modelList.forEach(this::buildResourcePath);
        return modelList;
    }


    @Override
    public UserModel buildResourcePath(UserModel model) {
        model.setWechatAvatar(RESOURCE_PATH_AVATAR + model.getWechatAvatar());
        return model;
    }
}
