package cn.pencilso.solitaire.common.exception;

import cn.pencilso.solitaire.common.constant.ResponeWrapperConst;

/**
 * @author pencilso
 * @date 2020/2/1 8:42 下午
 */
public class SolitaireAssertException extends SolitaireException {
    public SolitaireAssertException(String message) {
        super(message);
        setErrorCode(ResponeWrapperConst.ERROR_ASSERT);
    }
}