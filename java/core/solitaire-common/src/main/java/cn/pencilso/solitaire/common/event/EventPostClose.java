package cn.pencilso.solitaire.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author pencilso
 * @date 2020/2/14 7:52 上午
 */
public class EventPostClose extends ApplicationEvent {
    private Long postMid;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public EventPostClose(Object source,Long postMid) {
        super(source);
        this.postMid = postMid;
    }

    public Long getPostMid() {
        return postMid;
    }
}
