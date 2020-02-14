package cn.pencilso.solitaire.solitaireservice.listener;

import cn.pencilso.solitaire.solitairedao.service.PostService;
import cn.pencilso.solitaire.common.event.EventPostClose;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author pencilso
 * @date 2020/2/14 7:56 上午
 */
@Slf4j
@Component
public class EventPostCloseListener implements ApplicationListener<EventPostClose> {
    @Autowired
    private PostService postService;

    @Override
    public void onApplicationEvent(EventPostClose event) {
        boolean postClose = postService.postClose(event.getPostMid());
        log.debug("listener received close post event postMid: [{}] close status : [{}]", event.getPostMid(), postClose);
    }
}
