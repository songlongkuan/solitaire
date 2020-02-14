package cn.pencilso.solitaire.solitaireappservice.impl;

import cn.pencilso.solitaire.common.exception.SolitaireMessageException;
import cn.pencilso.solitaire.common.model.auth.JwtAuthModel;
import cn.pencilso.solitaire.common.model.param.ParamPostInModel;
import cn.pencilso.solitaire.common.model.param.ParamPostReleaseModel;
import cn.pencilso.solitaire.common.model.view.ViewPostDetailModel;
import cn.pencilso.solitaire.common.model.view.ViewPostInModel;
import cn.pencilso.solitaire.common.model.view.ViewPostModel;
import cn.pencilso.solitaire.common.toolkit.id.IdGeneratorCore;
import cn.pencilso.solitaire.solitaireappservice.PostControllerService;
import cn.pencilso.solitaire.solitairedao.model.PostInModel;
import cn.pencilso.solitaire.solitairedao.model.PostModel;
import cn.pencilso.solitaire.solitairedao.model.UserModel;
import cn.pencilso.solitaire.solitairedao.service.PostInService;
import cn.pencilso.solitaire.solitairedao.service.PostService;
import cn.pencilso.solitaire.solitairedao.service.UserService;
import cn.pencilso.solitaire.common.event.EventPostClose;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pencilso
 * @date 2020/2/13 9:35 上午
 */
@Validated
@Slf4j
@Service
public class PostControllerServiceImpl implements PostControllerService {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostInService postInService;

    @Override
    public Optional<Long> releasePost(@NotNull ParamPostReleaseModel postReleaseModel, @NotNull JwtAuthModel jwtAuthModel) {
        PostModel postModel = new PostModel();
        BeanUtils.copyProperties(postReleaseModel, postModel);
        postModel.setPostCreateUmid(jwtAuthModel.getUMid());
        postModel.setMid(IdGeneratorCore.generatorId());
        if (postService.save(postModel)) {
            return Optional.of(postModel.getMid());
        }
        return Optional.empty();
    }

    @Override
    public Page<PostModel> queryPagePostByCreateuMid(Page page, @NotNull Long uMid) {
        LambdaQueryWrapper<PostModel> postModelLambdaQueryWrapper = Wrappers.lambdaQuery(new PostModel())
                .eq(PostModel::getPostCreateUmid, uMid)
                .orderByDesc(PostModel::getCreateDate);
        return postService.page(page, postModelLambdaQueryWrapper);
    }

    @Override
    public Page<PostModel> queryExpirePostList(Page page) {
        return postService.queryExpirePostList(page);
    }

    @Override
    public List<ViewPostModel> postModel2ViewModel(Collection<PostModel> postModels) {
        List<ViewPostModel> viewPostModels = new ArrayList<>(postModels.size());
        postModels.forEach(postModel -> viewPostModels.add(postModel.buildViewModel()));
        return viewPostModels;
    }

    @Override
    public ViewPostDetailModel queryPostDetail(@NotNull Long postMid, @NotNull Long uMid) {
        PostModel postModel = postService.getByMid(postMid).orElseThrow(() -> new SolitaireMessageException("该接龙不存在,或已被删除 ！"));
        UserModel userModel = userService.getByMid(postModel.getPostCreateUmid()).orElseThrow(() -> new SolitaireMessageException("接龙创建人查询失败！"));

        userModel = userService.buildResourcePath(userModel);


        ViewPostDetailModel viewPostDetailModel = new ViewPostDetailModel();
        viewPostDetailModel.setPostModel(postModel.buildViewModel());
        viewPostDetailModel.setUserModel(userModel.buildViewModel());

        viewPostDetailModel.setAuthor(userModel.getMid().equals(uMid));

        postInService.getByPostMidAnduMid(postMid, uMid).ifPresent(postInModel -> {
            viewPostDetailModel.setPostInContent(postInModel.getPostInContent());
        });
        //检查该接龙是否过期
        if (
                !postModel.getPostCloseJoin()
                        && postModel.getPostCloseTime() != null
                        && System.currentTimeMillis() > postModel.getPostCloseTime().getTime()
        ) {
            pushPostCloseEvent(postMid);
            viewPostDetailModel.getPostModel().setPostCloseJoin(true);
            log.debug("query post detail find close time is expire  so close this post  postMid: [{}]  ", postMid);
        }
        return viewPostDetailModel;
    }

    @Transactional
    @Override
    public boolean postIn(@NotNull ParamPostInModel paramPostInModel, @NotNull JwtAuthModel jwtAuthModel) {
        PostModel postModel = postService.getByMid(paramPostInModel.getPostMid()).orElseThrow(() -> new SolitaireMessageException("该接龙帖不存在或被删除！"));
        if (postModel.getPostCloseJoin()) {
            throw new SolitaireMessageException("该接龙已经截止了！");
        }
        if (postModel.getPostClosePernum() != 0 && postModel.getPostNumJoin() >= postModel.getPostClosePernum()) {
            throw new SolitaireMessageException("该接龙人数已经截止了！");
        }
        if (System.currentTimeMillis() > postModel.getPostCloseTime().getTime()) {
            throw new SolitaireMessageException("该接龙时间已经截止了！");
        }

        Optional<PostInModel> postInModelOptional = postInService.getByPostMidAnduMid(paramPostInModel.getPostMid(), jwtAuthModel.getUMid());
        if (postInModelOptional.isPresent()) {
            //该用户已经参与过了  更新内容即可
            PostInModel postInModel = new PostInModel();
            postInModel.setPostInContent(paramPostInModel.getContent());
            postInModel.setVersion(postInModelOptional.get().getVersion());
            boolean updateInPostContent = postInService.updateModelByMid(postInModel, postInModelOptional.get().getMid());
            log.debug("updateInPostContent status : [{}]", updateInPostContent);
            return true;
        } else {
            PostInModel postInModel = new PostInModel();
            postInModel.setPostMid(paramPostInModel.getPostMid());
            postInModel.setPostInContent(paramPostInModel.getContent());
            postInModel.setPostInUmid(jwtAuthModel.getUMid());
            postInModel.setMid(IdGeneratorCore.generatorId());
            postInModel.setPostOrder(postModel.getPostNumJoin() + 1);
            if (!postInService.save(postInModel)) {
                throw new SolitaireMessageException("加入接龙失败，请尝试稍后重试！");
            }

            PostModel updatePostModel = new PostModel();
            if (postModel.getPostClosePernum() != 0 && postModel.getPostNumJoin() == (postModel.getPostClosePernum() - 1)) {
                //判断是否是最后一个加入者 截止接龙
                updatePostModel.setPostCloseJoin(true);
            }
            //参与人数 +1
            updatePostModel.setPostNumJoin(postModel.getPostNumJoin() + 1);
            updatePostModel.setVersion(postModel.getVersion());
            if (!postService.updateModelByMid(updatePostModel, paramPostInModel.getPostMid())) {
                throw new SolitaireMessageException("操作失败，请尝试稍后重试！");
            }
            return true;
        }

    }


    @Override
    public List<ViewPostInModel> queryPostInList(Page page, @NotNull Long postMid) {
        Page postPage = postInService.page(page, Wrappers.lambdaQuery(new PostInModel()).eq(PostInModel::getPostMid, postMid).orderByDesc(PostInModel::getPostOrder));


        List<PostInModel> postInModels = postPage.getRecords();
        if (postInModels.isEmpty()) return new ArrayList<>();
        List<Long> uMids = postInModels.stream().map(PostInModel::getPostInUmid).collect(Collectors.toList());
        //查询用户列表
        Map<Long, UserModel> userModelMap =
                (uMids.isEmpty() ? new ArrayList<UserModel>() : userService.buildResourcePath(userService.getByMids(uMids)))
                        .stream().collect(Collectors.toMap(UserModel::getMid, userModel -> userModel));

        List<ViewPostInModel> viewPostInModels = new ArrayList<>(postInModels.size());
        postInModels.forEach(postInModel -> {
            UserModel userModel = userModelMap.get(postInModel.getPostInUmid());

            ViewPostInModel viewPostInModel = new ViewPostInModel();
            viewPostInModel.setPostInContent(postInModel.getPostInContent());
            viewPostInModel.setPostInDate(postInModel.getCreateDate());
            viewPostInModel.setPostInOrder(postInModel.getPostOrder());
            viewPostInModel.setUserNikename(userModel.getNikeName());
            viewPostInModel.setUserAvatar(userModel.getWechatAvatar());
            viewPostInModels.add(viewPostInModel);

        });
        return viewPostInModels;
    }

    @Override
    public void pushPostCloseEvent(@NotNull Long postMid) {
        postService.pushPostCloseEvent(postMid);
    }

    @Override
    public String postExport(@NotNull Long postMid) {
        String lineProperty = System.getProperty("line.separator");
        PostModel postModel = postService.getByMid(postMid).orElseThrow(() -> new SolitaireMessageException("该接龙不存在，无法导出！"));
        List<PostInModel> inModelList = postInService.lambdaQuery().eq(PostInModel::getPostMid, postMid).orderByDesc(PostInModel::getPostOrder).list();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("本期接龙 ：").append(lineProperty).append("\"").append(postModel.getPostDesc()).append("\"").append(lineProperty)
                .append("参与人数：").append(postModel.getPostNumJoin()).append(lineProperty)
                .append("以下是接龙列表：").append(lineProperty);
        inModelList.stream().forEach(postInModel -> {
            stringBuilder.append(postInModel.getPostOrder()).append("、").append(postInModel.getPostInContent()).append(lineProperty);
        });
        return stringBuilder.toString();
    }

    @Override
    public Page<PostModel> queryPagePostByInuMid(@NotNull Page page, @NotNull Long uMid) {
        LambdaQueryWrapper<PostInModel> postModelLambdaQueryWrapper = Wrappers.lambdaQuery(new PostInModel())
                .eq(PostInModel::getPostInUmid, uMid)
                .orderByDesc(PostInModel::getCreateDate);
        Page<PostInModel> postInModelPage = postInService.page(page, postModelLambdaQueryWrapper);
        List<Long> postMids = postInModelPage.getRecords().stream().map(PostInModel::getPostMid).collect(Collectors.toList());

        List<PostModel> postModelList = postService.getByMids(postMids);
        Page<PostModel> postModelPage = new Page<>();
        BeanUtils.copyProperties(postInModelPage, postModelPage);
        postModelPage.setRecords(postModelList);
        return postModelPage;
    }

    @Override
    public Optional<PostModel> getPostModel(@NotNull Long postMid) {
        return postService.getByMid(postMid);
    }
}
