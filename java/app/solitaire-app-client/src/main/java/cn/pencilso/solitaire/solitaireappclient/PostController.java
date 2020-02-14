package cn.pencilso.solitaire.solitaireappclient;

import cn.pencilso.solitaire.common.base.BaseController;
import cn.pencilso.solitaire.common.exception.SolitaireMessageException;
import cn.pencilso.solitaire.common.model.auth.JwtAuthModel;
import cn.pencilso.solitaire.common.model.param.ParamPostInModel;
import cn.pencilso.solitaire.common.model.param.ParamPostReleaseModel;
import cn.pencilso.solitaire.common.model.view.ViewPostDetailModel;
import cn.pencilso.solitaire.common.model.view.ViewPostInModel;
import cn.pencilso.solitaire.common.model.view.ViewPostModel;
import cn.pencilso.solitaire.solitaireappservice.PostControllerService;
import cn.pencilso.solitaire.solitairedao.model.PostModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 接龙帖子
 *
 * @author pencilso
 * @date 2020/2/13 9:22 上午
 */
@Slf4j
@RestController
@RequestMapping("api/client/post")
@Validated
public class PostController extends BaseController {

    @Autowired
    private PostControllerService postControllerService;

    /**
     * 发布接龙帖子
     *
     * @param postReleaseModel
     * @return
     */
    @PostMapping("post_release")
    public Long postRelease(@Valid ParamPostReleaseModel postReleaseModel) {
        return postControllerService.releasePost(postReleaseModel, jwtAuthModel().get())
                .orElseThrow(() -> new SolitaireMessageException("发布接龙失败，请尝试稍后重试！"));
    }

    /**
     * 查询我发布的帖子
     *
     * @return
     */
    @GetMapping("query_myrelease")
    public List<ViewPostModel> queryMyRelease() {
        Long uMid = jwtAuthModel().map(JwtAuthModel::getUMid).get();
        Page<PostModel> postModelPage = postControllerService.queryPagePostByCreateuMid(buildPage(), uMid);
        //转视图层 model
        return postControllerService.postModel2ViewModel(postModelPage.getRecords());
    }

    /**
     * 查询 我参与的
     *
     * @return
     */
    @GetMapping("query_myin")
    public List<ViewPostModel> queryMyIn() {
        Long uMid = jwtAuthModel().map(JwtAuthModel::getUMid).get();
        Page<PostModel> postModelPage = postControllerService.queryPagePostByInuMid(buildPage(), uMid);
        //转视图层 model
        return postControllerService.postModel2ViewModel(postModelPage.getRecords());
    }


    /**
     * 查询 接龙帖子详情
     *
     * @param postMid
     * @return
     */
    @GetMapping("query_postdetail/{postMid}")
    public ViewPostDetailModel queryPostDetail(@PathVariable("postMid") Long postMid) {
        return postControllerService.queryPostDetail(postMid, jwtAuthModel().get().getUMid());
    }

    /**
     * 加入接龙 帖子
     *
     * @return
     */
    @PostMapping("post_in")
    public Boolean postIn(@Valid ParamPostInModel paramPostInModel) {
        return postControllerService.postIn(paramPostInModel, jwtAuthModel().get());
    }

    /**
     * 查询 接龙列表
     *
     * @return
     */
    @GetMapping("query_postinlist/{postMid}")
    public List<ViewPostInModel> queryPostInList(@PathVariable("postMid") Long postMid) {
        return postControllerService.queryPostInList(buildPage(), postMid);
    }

    /**
     * 手动截止 接龙活动
     *
     * @param postMid
     * @return
     */
    @GetMapping("close_post/{postMid}")
    public Boolean closePost(@PathVariable("postMid") Long postMid) {
        PostModel postModel = postControllerService.getPostModel(postMid).orElseThrow(() -> new SolitaireMessageException("该接龙不存在或已被删除！"));
        if (!postModel.getPostCreateUmid().equals(jwtAuthModel().get().getUMid())) {
            throw new SolitaireMessageException("只有发起者可以手动截止接龙！");
        }
        postControllerService.pushPostCloseEvent(postMid);
        return true;
    }

    /**
     * 导出接龙数据
     *
     * @return
     */
    @GetMapping("post_export/{postMid}")
    public String postExport(@PathVariable Long postMid) {
        return postControllerService.postExport(postMid);
    }

}
