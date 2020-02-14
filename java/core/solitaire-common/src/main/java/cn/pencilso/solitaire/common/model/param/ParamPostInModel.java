package cn.pencilso.solitaire.common.model.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 加入接龙的参数
 * @author pencilso
 * @date 2020/2/13 6:21 下午
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class ParamPostInModel {
    /**
     * 接龙内容
     */
    private String content;

    /**
     * 接龙帖子ID
     */
    private Long postMid;
}
