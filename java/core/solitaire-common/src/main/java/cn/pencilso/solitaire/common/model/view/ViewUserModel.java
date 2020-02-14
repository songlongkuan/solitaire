package cn.pencilso.solitaire.common.model.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author pencilso
 * @date 2020/2/13 4:47 下午
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ViewUserModel {
    private Long uMid;

    private String nikeName;

    /**
     * 微信用户头像
     */
    private String wechatAvatar;

    /**
     * 微信-> 性别
     */
    private Integer wechatGender;
}
