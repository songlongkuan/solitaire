package cn.pencilso.solitaire.common.model.view;

import lombok.Data;

import java.util.Date;

/**
 * @author pencilso
 * @date 2020/2/13 7:17 下午
 */
@Data
public class ViewPostInModel {
    /**
     * 用户 昵称
     */
    private String userNikename;
    /**
     * 用户 头像
     */
    private String userAvatar;
    /**
     * 接龙 排序
     */
    private Integer postInOrder;
    /**
     * 接龙 内容
     */
    private String postInContent;
    /**
     * 接龙时间
     */
    private Date postInDate;
}