package cn.pencilso.solitaire.solitaireservice.configuration;

import cn.pencilso.solitaire.common.constant.ResponeWrapperConst;
import cn.pencilso.solitaire.common.model.respone.ResponeWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 拦截器 统一返回数据结构
 *
 * @author pencilso
 * @date 2020/2/10 8:20 下午
 */
@EnableWebMvc
@Configuration
public class UnifiedReturnConfig {

    @RestControllerAdvice("cn.pencilso.solitaire.solitaireappclient")
    static class CommonResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
            if (body instanceof ResponeWrapper) {
                return body;
            }
            ResponeWrapper<Object> responeWrapper = new ResponeWrapper<>()
                    .setCode(ResponeWrapperConst.SUCCESS)
                    .setMessage("操作成功")
                    .setData(body);
            return responeWrapper;
        }
    }
}