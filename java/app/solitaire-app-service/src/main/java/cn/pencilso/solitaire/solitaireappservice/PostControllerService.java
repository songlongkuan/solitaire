package cn.pencilso.solitaire.solitaireappservice;

import cn.pencilso.solitaire.common.model.auth.JwtAuthModel;
import cn.pencilso.solitaire.common.model.param.ParamPostInModel;
import cn.pencilso.solitaire.common.model.param.ParamPostReleaseModel;
import cn.pencilso.solitaire.common.model.view.ViewPostDetailModel;
import cn.pencilso.solitaire.common.model.view.ViewPostInModel;
import cn.pencilso.solitaire.common.model.view.ViewPostModel;
import cn.pencilso.solitaire.solitairedao.model.PostModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/13 9:34 上午
 */
public interface PostControllerService {


    /**
     * 发布接龙帖子
     *
     * @param postReleaseModel
     * @param jwtAuthModel
     * @return
     */
    Optional<Long> releasePost(@NotNull ParamPostReleaseModel postReleaseModel, @NotNull JwtAuthModel jwtAuthModel);

    /**
     * 根据创建人用户ID 分页查询
     *
     * @param page 分页参数
     * @param uMid 创建人 用户ID
     * @return
     */
    Page<PostModel> queryPagePostByCreateuMid(Page page, @NotNull Long uMid);

    /**
     * 查询过期的接龙
     * @param page
     * @return
     */
    Page<PostModel> queryExpirePostList(Page page);


    /**
     * db model 转 view model
     *
     * @param postModels
     * @return
     */
    List<ViewPostModel> postModel2ViewModel(Collection<PostModel> postModels);

    /**
     * 查询 接龙帖子详情
     *
     * @param postMid
     * @param uMid
     * @return
     */
    ViewPostDetailModel queryPostDetail(@NotNull Long postMid, @NotNull Long uMid);

    /**
     * 加入接龙帖子
     *
     * @param paramPostInModel
     * @param jwtAuthModel
     * @return
     */
    boolean postIn(@NotNull ParamPostInModel paramPostInModel, @NotNull JwtAuthModel jwtAuthModel);

    /**
     * 查询 接龙列表
     *
     * @param page
     * @param postMid
     * @return
     */
    List<ViewPostInModel> queryPostInList(Page page, @NotNull Long postMid);

    /**
     * 发布截止接龙活动的event
     *
     * @param postMid
     * @return
     */
    void pushPostCloseEvent(@NotNull Long postMid);

    /**
     * 导出接龙
     *
     * @param postMid
     * @return
     */
    String postExport(@NotNull Long postMid);

    /**
     * 分页查询 我参与的
     * @param buildPage
     * @param uMid
     * @return
     */
    Page<PostModel> queryPagePostByInuMid(@NotNull Page buildPage, @NotNull Long uMid);

    /**
     * 查询接龙
     * @param postMid
     * @return
     */
    Optional<PostModel> getPostModel(@NotNull Long postMid);
}
