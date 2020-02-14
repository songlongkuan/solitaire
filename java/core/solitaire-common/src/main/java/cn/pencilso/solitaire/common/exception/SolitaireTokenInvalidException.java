package cn.pencilso.solitaire.common.exception;

import cn.pencilso.solitaire.common.constant.ResponeWrapperConst;

/**
 * 登录失效 异常
 *
 * @author pencilso
 * @date 2020/1/25 11:09 上午
 */
public class SolitaireTokenInvalidException extends SolitaireMessageException {
    public SolitaireTokenInvalidException() {
        super("登录已失效，请尝试重新登录！");
        setErrorCode(ResponeWrapperConst.ERROR_TOKEN_INVALID);
    }

}
