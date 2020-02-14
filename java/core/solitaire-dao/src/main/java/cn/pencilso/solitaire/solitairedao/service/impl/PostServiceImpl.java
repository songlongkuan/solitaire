package cn.pencilso.solitaire.solitairedao.service.impl;

import cn.pencilso.solitaire.common.event.EventPostClose;
import cn.pencilso.solitaire.solitairedao.model.PostModel;
import cn.pencilso.solitaire.solitairedao.mapper.PostMapper;
import cn.pencilso.solitaire.solitairedao.service.PostService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 接龙帖子 服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-12
 */
@Service
@Validated
public class PostServiceImpl extends ServiceImpl<PostMapper, PostModel> implements PostService {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public boolean postClose(@NotNull Long postMid) {
        PostModel postModel = new PostModel().setPostCloseJoin(true);
        return updateModelByMid(postModel, postMid);
    }

    @Override
    public Page<PostModel> queryExpirePostList(Page page) {
        LambdaQueryWrapper<PostModel> postModelLambdaQueryWrapper = Wrappers.lambdaQuery(new PostModel())
                .eq(PostModel::getPostCloseJoin, false)
                .lt(PostModel::getPostCloseTime, new Date())
                .orderByAsc(PostModel::getCreateDate);
        Page<PostModel> postModelPage = this.page(page, postModelLambdaQueryWrapper);
        return postModelPage;
    }

    @Override
    public void pushPostCloseEvent(@NotNull Long postMid) {
        EventPostClose eventPostClose = new EventPostClose(this, postMid);
        eventPublisher.publishEvent(eventPostClose);
    }
}
