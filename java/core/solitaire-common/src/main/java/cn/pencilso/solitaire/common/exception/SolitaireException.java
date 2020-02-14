package cn.pencilso.solitaire.common.exception;

/**
 * @author pencilso
 * @date 2020/1/25 11:10 上午
 */
public class SolitaireException extends RuntimeException {
    private int errorCode;

    protected SolitaireException(String message) {
        super(message);
    }


    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
