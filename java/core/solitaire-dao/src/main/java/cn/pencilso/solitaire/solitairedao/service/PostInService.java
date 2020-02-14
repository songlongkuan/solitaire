package cn.pencilso.solitaire.solitairedao.service;

import cn.pencilso.solitaire.solitairedao.model.PostInModel;
import cn.pencilso.solitaire.solitairedao.base.BaseIService;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-13
 */
public interface PostInService extends BaseIService<PostInModel> {
    /**
     * 根据接龙id 和用户id 查询是否参与
     *
     * @param postMid
     * @param uMid
     * @return
     */
    Optional<PostInModel> getByPostMidAnduMid(@NotNull Long postMid, @NotNull Long uMid);
}
