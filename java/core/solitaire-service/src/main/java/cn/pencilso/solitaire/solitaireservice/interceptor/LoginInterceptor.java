package cn.pencilso.solitaire.solitaireservice.interceptor;

import cn.pencilso.solitaire.common.annotation.ClearRight;
import cn.pencilso.solitaire.common.exception.SolitaireMessageException;
import cn.pencilso.solitaire.common.exception.SolitaireTokenExpireException;
import cn.pencilso.solitaire.common.exception.SolitaireTokenInvalidException;
import cn.pencilso.solitaire.common.model.auth.JwtAuthModel;
import cn.pencilso.solitaire.common.plugin.JwtPlugin;
import cn.pencilso.solitaire.solitairedao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author pencilso
 * @date 2020/2/12 9:22 下午
 */
@Slf4j
@Aspect
@Component
public class LoginInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtPlugin jwtPlugin;

    /**
     * 定义拦截规则：拦截com.xjj.web.controller包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* cn.pencilso.solitaire.solitaireappclient..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {

    }


    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("controllerMethodPointcut()") //指定拦截器规则；也可以直接把“execution(* com.xjj.........)”写进这里
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        ClearRight manageLogin = method.getAnnotation(ClearRight.class);
        if (manageLogin != null) {
            //清除授权检查
            return pjp.proceed();
        }


        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest httpServletRequest = sra.getRequest();

        String accesstoken = httpServletRequest.getHeader("accesstoken");
        if (StringUtils.isEmpty(accesstoken)) {
            //accesstoken字段为空
            throw new SolitaireTokenInvalidException();
        }
        JwtAuthModel jwtAuthModel = jwtPlugin.getOauthEntity(accesstoken).orElseThrow(SolitaireTokenExpireException::new);
//        //该用户的salt 找不到
//        String jwtSalt = userService.getJwtSalt(jwtAuthModel.getUMid()).orElseThrow(() -> new SolitaireMessageException("找不到当前用户的数据，请尝试重新登录！"));
//        if (!jwtAuthModel.getSalt().equals(jwtSalt)) {
//            throw new SolitaireTokenExpireException();
//        }
        httpServletRequest.setAttribute("jwtAuthModel", jwtAuthModel);
        return pjp.proceed();
    }
}
