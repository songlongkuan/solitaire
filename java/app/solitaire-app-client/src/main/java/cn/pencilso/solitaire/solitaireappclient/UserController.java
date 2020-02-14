package cn.pencilso.solitaire.solitaireappclient;

import cn.pencilso.solitaire.common.annotation.ClearRight;
import cn.pencilso.solitaire.common.base.BaseController;
import cn.pencilso.solitaire.common.model.auth.JwtAuthModel;
import cn.pencilso.solitaire.common.toolkit.Kv;
import cn.pencilso.solitaire.solitaireappservice.UserControllerService;
import cn.pencilso.solitaire.solitairedao.model.UserModel;
import cn.pencilso.solitaire.solitairedao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/10 8:05 下午
 */
@Slf4j
@RestController
@RequestMapping("api/client/user")
@Validated
public class UserController extends BaseController {

    @Autowired
    private UserControllerService userControllerService;
    @Autowired
    private UserService userService;

    /**
     * 微信登录
     *
     * @param wechatCode
     * @param wechatEncryptedData
     * @param wechatVi
     * @return
     */
    @ClearRight
    @PostMapping("wechatlogin")
    public Map wechatLogin(@NotNull String wechatCode, @NotBlank String wechatEncryptedData, @NotBlank String wechatVi) {
        return Kv.stringStringKv().set("accesstoken", userControllerService.wechatLogin(wechatCode, wechatEncryptedData, wechatVi));
    }


}