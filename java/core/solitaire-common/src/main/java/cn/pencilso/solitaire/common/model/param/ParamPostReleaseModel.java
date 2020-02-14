package cn.pencilso.solitaire.common.model.param;

import cn.pencilso.solitaire.common.toolkit.DateUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author pencilso
 * @date 2020/2/13 9:24 上午
 */
@Data
public class ParamPostReleaseModel {
    /**
     * 帖子描述
     */
    @NotBlank(message = "接龙描述不能为空")
    @Length(min = 1, max = 255, message = "描述内容长度应在 255个字符以内！")
    private String postDesc;

    /**
     * 截止人数
     */
    private Integer postClosePernum;

    /**
     * 截止时间
     */
    @NotNull(message = "截止时间不能为空！")
    @DateTimeFormat(pattern = DateUtils.PatternConst.PATTERN_YYY_MM_DD_HH_MM)
    private Date postCloseTime;

}
