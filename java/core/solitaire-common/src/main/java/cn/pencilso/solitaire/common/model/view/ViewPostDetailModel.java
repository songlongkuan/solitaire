package cn.pencilso.solitaire.common.model.view;

import lombok.Data;

/**
 * @author pencilso
 * @date 2020/2/13 4:31 下午
 */
@Data
public class ViewPostDetailModel {
    /**
     * 接龙帖子 view 视图 model
     */
    private ViewPostModel postModel;
    /**
     * 用户view 视图model
     */
    private ViewUserModel userModel;

    /**
     * 当前用户是否是作者
     */
    private boolean isAuthor;
    /**
     * 接龙内容
     */
    private String postInContent;
}
