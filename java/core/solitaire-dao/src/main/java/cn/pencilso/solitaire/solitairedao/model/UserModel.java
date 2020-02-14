package cn.pencilso.solitaire.solitairedao.model;

import cn.pencilso.solitaire.common.model.view.ViewUserModel;
import cn.pencilso.solitaire.solitairedao.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 
 * </p>
 *
 * @author pencilso
 * @since 2020-02-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("solitaire_user")
public class UserModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String nikeName;

    private String wechatOpenid;

    private String wechatUnionId;

    private String wechatAccesstoken;

    private String wechatSessionKey;
    /**
     * 微信用户头像
     */
    private String wechatAvatar;

    /**
     * jwt access token
     */
    private String jwtAccesstoken;

    /**
     * jwt 哈希盐
     */
    private String jwtSalt;

    /**
     * 微信 -> 城市
     */
    private String wechatCity;

    /**
     * 微信-> 省份
     */
    private String wechatProvince;

    /**
     * 微信-> 性别
     */
    private Integer wechatGender;



    public ViewUserModel buildViewModel() {
        ViewUserModel viewUserModel = new ViewUserModel();
        BeanUtils.copyProperties(this, viewUserModel);
        viewUserModel.setUMid(this.getMid());
        return viewUserModel;
    }
}
