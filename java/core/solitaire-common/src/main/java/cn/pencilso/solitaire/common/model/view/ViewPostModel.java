package cn.pencilso.solitaire.common.model.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author pencilso
 * @date 2020/2/13 12:02 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ViewPostModel {
    /**
     * 接龙帖子唯一ID
     */
    private Long postMid;

    /**
     * 帖子描述
     */
    private String postDesc;

    /**
     * 截止人数
     */
    private Integer postClosePernum;

    /**
     * 截止时间
     */
    private Date postCloseTime;

    /**
     * 帖子 截止加入  0未截止  1已截止
     */
    private Boolean postCloseJoin;

    /**
     * 帖子加入人数
     */
    private Integer postNumJoin;

    /**
     * 创建时间
     */
    private Date createDate;
}