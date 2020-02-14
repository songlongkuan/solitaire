package cn.pencilso.solitaire.solitairedao.service;

import cn.pencilso.solitaire.solitairedao.model.PostModel;
import cn.pencilso.solitaire.solitairedao.base.BaseIService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 接龙帖子 服务类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-12
 */
public interface PostService extends BaseIService<PostModel> {

    /**
     * 截止接龙活动
     *
     * @param postMid
     * @return
     */
    boolean postClose(@NotNull Long postMid);

    /**
     * 查询已过期的 post 接龙列表
     * @param page
     * @return
     */
    Page<PostModel> queryExpirePostList(Page page);

    /**
     * 发布截止接龙活动的event
     *
     * @param postMid
     * @return
     */
    void pushPostCloseEvent(@NotNull Long postMid);
}
