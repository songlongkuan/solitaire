package cn.pencilso.solitaire.common.handler.exception;

import cn.pencilso.solitaire.common.constant.ResponeWrapperConst;
import cn.pencilso.solitaire.common.exception.*;
import cn.pencilso.solitaire.common.model.respone.ResponeWrapper;
import cn.pencilso.solitaire.common.toolkit.JsonUtils;
import cn.pencilso.solitaire.common.toolkit.Kv;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * Created by SongLongKuan on 2018/6/19/019.
 *
 * @author SongLongKuan
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 超时
     *
     * @param httpServletRequest
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SocketTimeoutException.class)
    @ResponseBody
    public ResponeWrapper<Object> socketTimeOut(HttpServletRequest httpServletRequest, Exception ex) {
        log.debug("socket time out url: [{}]", httpServletRequest.getRequestURI(), ex);
        return new ResponeWrapper<>()
                .setCode(ResponeWrapperConst.ERROR_TIME_OUT)
                .setMessage("超时出错，请尝试稍后重试！");
    }

    /**
     * 找不到请求方式
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponeWrapper<Object> notSupporteException(HttpServletRequest httpServletRequest, HttpRequestMethodNotSupportedException e) {
        log.debug("no requer mapping controller url: [{}]", httpServletRequest.getRequestURI(), e);
        return new ResponeWrapper<>()
                .setCode(ResponeWrapperConst.ERROR_MAPPING_NOT_FOUND)
                .setMessage("路由不匹配 ！");
    }


    /**
     * 请求参数错误
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MissingServletRequestParameterException.class, org.springframework.web.multipart.MultipartException.class, MissingServletRequestPartException.class, MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponeWrapper<Object> requestParameterException(HttpServletRequest httpServletRequest, Exception e) {
        log.debug("param exception url: [{}]", httpServletRequest.getRequestURI(), e);
        return new ResponeWrapper<>()
                .setCode(ResponeWrapperConst.ERROR_PARAM_VALIDATION)
                .setMessage(e.getMessage());
    }

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({SolitaireAssertException.class, SolitaireMessageException.class, SolitaireTokenInvalidException.class, SolitaireTokenExpireException.class})
    @ResponseBody
    public ResponeWrapper<Object> accessErrorException(HttpServletRequest httpServletRequest, SolitaireException e) {
        log.debug("custom error url: [{}]", httpServletRequest.getRequestURI(), e);
        return new ResponeWrapper<>()
                .setCode(e.getErrorCode())
                .setMessage(e.getMessage());
    }

    /**
     * 参数校验出粗
     *
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponeWrapper<Object> violationExcetion(HttpServletRequest httpServletRequest, ConstraintViolationException ex) {
        log.debug("param violationExcetion url: [{}]", httpServletRequest.getRequestURI(), ex);
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        if (iterator.hasNext()) {
            ConstraintViolation<?> next = iterator.next();
            String message = next.getMessage();
            return new ResponeWrapper<>()
                    .setCode(ResponeWrapperConst.ERROR_PARAM_VALIDATION)
                    .setMessage(message);
        }
        return new ResponeWrapper<>()
                .setCode(ResponeWrapperConst.ERROR_PARAM_VALIDATION)
                .setMessage(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResponeWrapper<Object> bindingResultError(BindException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new ResponeWrapper<>()
                .setCode(ResponeWrapperConst.ERROR_PARAM_VALIDATION)
                .setMessage(message);
    }

    /**
     * 所有的异常
     *
     * @param httpServletRequest
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponeWrapper<Object> exceptionAll(HttpServletRequest httpServletRequest, Exception ex) throws JsonProcessingException {
        ex.printStackTrace();
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        LinkedHashMap headMap = new LinkedHashMap();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            headMap.put(s, httpServletRequest.getHeader(s));
        }
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        @NotNull String paramterStr = JsonUtils.objectToJson(Kv.stringObjectKv().set("head", headMap).set("parame", parameterMap));
        log.warn("Unknown error url: [{}]  paramter: [{}]", httpServletRequest.getRequestURI(), paramterStr, ex);
        return new ResponeWrapper<>()
                .setCode(ResponeWrapperConst.ERROR_SERVER_ERROR)
                .setMessage("服务器异常，我们将会尽快处理。");
    }


}