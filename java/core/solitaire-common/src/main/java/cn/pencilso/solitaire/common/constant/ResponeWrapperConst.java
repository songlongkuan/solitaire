package cn.pencilso.solitaire.common.constant;

/**
 * 返回错误码常量
 *
 * @author pencilso
 * @date 2020/1/24 4:24 下午
 */
public interface ResponeWrapperConst {

    int SUCCESS = 0;
    /**
     * 操作失败
     */
    int ERROR_OPERATE_FAIL = 10001;
    /**
     * 参数校验失败
     */
    int ERROR_PARAM_VALIDATION = 10002;


    /**
     * 找不到api路由
     */
    int ERROR_MAPPING_NOT_FOUND = 10004;


    /**
     * 响应超时
     */
    int ERROR_TIME_OUT = 11001;


    /**
     * 服务器异常
     */
    int ERROR_SERVER_ERROR = 20000;


// --------自定义异常

    /**
     * 自定义错误信息返回
     */
    int ERROR_CUSTOM_MESSAGE = 12001;

    /**
     * 断言异常
     */
    int ERROR_ASSERT = 12002;

    /**
     * 登录过期
     */
    int ERROR_TOKEN_EXPIRE = 13001;
    /**
     * 未登录
     */
    int ERROR_TOKEN_INVALID = 13002;
}