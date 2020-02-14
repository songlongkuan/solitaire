package cn.pencilso.solitaire.solitairedao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.pencilso.solitaire.solitairedao.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author pencilso
 * @since 2020-02-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("solitaire_post_in")
public class PostInModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 接龙 帖子唯一ID
     */
    private Long postMid;

    /**
     * 加入帖子的用户ID
     */
    private Long postInUmid;

    /**
     * 加入接龙提交的内容
     */
    private String postInContent;

    /**
     * 当前用户所在接龙的排序
     */
    private Integer postOrder;

}
