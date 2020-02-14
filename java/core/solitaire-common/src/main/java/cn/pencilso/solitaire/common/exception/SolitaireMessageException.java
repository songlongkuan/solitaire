package cn.pencilso.solitaire.common.exception;

import cn.pencilso.solitaire.common.constant.ResponeWrapperConst;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author pencilso
 * @date 2020/1/23 8:55 下午
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SolitaireMessageException extends SolitaireException {


    public SolitaireMessageException(String message) {
        super(message);
        setErrorCode(ResponeWrapperConst.ERROR_CUSTOM_MESSAGE);
    }
}
