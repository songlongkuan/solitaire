package cn.pencilso.solitaire.solitairedao.model;

import cn.pencilso.solitaire.common.model.view.ViewPostModel;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.pencilso.solitaire.solitairedao.base.BaseModel;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 接龙帖子
 * </p>
 *
 * @author pencilso
 * @since 2020-02-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("solitaire_post")
public class PostModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子描述
     */
    private String postDesc;

    /**
     * 截止人数
     */
    private Integer postClosePernum;

    /**
     * 截止时间
     */
    private Date postCloseTime;

    /**
     * 帖子 截止加入  0未截止  1已截止
     */
    private Boolean postCloseJoin;

    /**
     * 帖子加入人数
     */
    private Integer postNumJoin;

    /**
     * 帖子创建人
     */
    private Long postCreateUmid;

    /**
     * 转视图view model
     *
     * @return
     */
    public ViewPostModel buildViewModel() {
        ViewPostModel viewPostModel = new ViewPostModel();
        BeanUtils.copyProperties(this, viewPostModel);
        viewPostModel.setPostMid(this.getMid());
        return viewPostModel;
    }
}
