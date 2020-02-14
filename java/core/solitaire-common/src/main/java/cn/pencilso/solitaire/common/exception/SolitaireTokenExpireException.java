package cn.pencilso.solitaire.common.exception;

import cn.pencilso.solitaire.common.constant.ResponeWrapperConst;

/**
 * 登录失效 异常
 *
 * @author pencilso
 * @date 2020/1/25 11:09 上午
 */
public class SolitaireTokenExpireException extends SolitaireMessageException {
    public SolitaireTokenExpireException() {
        super("登录已过期，请尝试重新登录！");
        setErrorCode(ResponeWrapperConst.ERROR_TOKEN_EXPIRE);
    }

}
