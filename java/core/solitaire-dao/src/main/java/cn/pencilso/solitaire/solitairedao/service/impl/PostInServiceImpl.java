package cn.pencilso.solitaire.solitairedao.service.impl;

import cn.pencilso.solitaire.solitairedao.model.PostInModel;
import cn.pencilso.solitaire.solitairedao.mapper.PostInMapper;
import cn.pencilso.solitaire.solitairedao.service.PostInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-13
 */
@Service
public class PostInServiceImpl extends ServiceImpl<PostInMapper, PostInModel> implements PostInService {

    @Override
    public Optional<PostInModel> getByPostMidAnduMid(@NotNull Long postMid, @NotNull Long uMid) {
        PostInModel postInModel = lambdaQuery()
                .eq(PostInModel::getPostMid, postMid)
                .eq(PostInModel::getPostInUmid, uMid)
                .one();
        return Optional.ofNullable(postInModel);
    }
}
