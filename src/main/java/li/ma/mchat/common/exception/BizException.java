package li.ma.mchat.common.exception;

import lombok.Getter;

/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
public class BizException extends Exception {
    @Getter
    private String logMsg;

    public BizException() {
    }

    public BizException(String message) {
        this(message, "");
    }
    public BizException(String message,String logMsg) {
        super(message);
        this.logMsg = logMsg;
    }


}
