package cn.pencilso.solitaire.common.base;

import cn.pencilso.solitaire.common.model.auth.JwtAuthModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/12 9:33 下午
 */
public class BaseController {
    @Autowired
    protected HttpServletResponse httpServletResponse;
    @Autowired
    protected HttpServletRequest httpServletRequest;

    /**
     * 获取授权
     *
     * @return
     */
    public Optional<JwtAuthModel> jwtAuthModel() {
        JwtAuthModel jwtAuthModel = (JwtAuthModel) httpServletRequest.getAttribute("jwtAuthModel");
        return Optional.ofNullable(jwtAuthModel);
    }

    public Page buildPage(boolean count) {
        return new Page(pageNum(), pageSize(), count);
    }

    public Page buildPage() {
        return buildPage(false);
    }


    public int pageNum() {
        return Integer.parseInt(Optional.ofNullable(httpServletRequest.getParameter("pageNum")).orElse("1"));
    }

    public int pageSize() {
        return Integer.parseInt(Optional.ofNullable(httpServletRequest.getParameter("pageSize")).orElse("20"));
    }

}
