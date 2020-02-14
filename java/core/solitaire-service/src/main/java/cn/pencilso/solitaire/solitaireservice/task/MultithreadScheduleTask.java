package cn.pencilso.solitaire.solitaireservice.task;

import cn.pencilso.solitaire.common.config.SolitaireProperties;
import cn.pencilso.solitaire.common.model.wechat.WechatGetAccessTokenModel;
import cn.pencilso.solitaire.common.service.WechatService;
import cn.pencilso.solitaire.solitairedao.model.PostModel;
import cn.pencilso.solitaire.solitairedao.service.PostService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 多线程 定时任务
 *
 * @author pencilso
 * @date 2020/2/11 7:18 下午
 */
@Component
@EnableScheduling
@Slf4j
public class MultithreadScheduleTask {
    @Autowired
    private WechatService wechatService;
    @Autowired
    private SolitaireProperties solitaireProperties;
    @Autowired
    private PostService postService;

    /**
     * 重置微信的Access token
     * 一小时执行一次
     */
    @Async
    @Scheduled(fixedDelay = 3600000L, initialDelay = 3000L)
    public void restWechatAccesstoken() {
        Optional<WechatGetAccessTokenModel> accessToken = wechatService.getAccessToken();
        accessToken.ifPresent(wechatGetAccessTokenModel -> {
            solitaireProperties.setWechatAccesstoken(wechatGetAccessTokenModel.getAccessToken());
        });
        log.info("restWechatAccesstoken respone : [{}]", accessToken);
    }

    /**
     * 清理到期的接龙 post
     */
    @Scheduled(fixedDelay = 3000L, initialDelay = 3000L)
    public void clearExpirePost() {
        Page<PostModel> postModelPage = postService.queryExpirePostList(new Page(1, 100, false));
        List<PostModel> postModelPageRecords = postModelPage.getRecords();
        if (postModelPageRecords.isEmpty()) return;
        postModelPage.getRecords().forEach(postModel -> {
            postService.pushPostCloseEvent(postModel.getMid());
        });
    }

}
