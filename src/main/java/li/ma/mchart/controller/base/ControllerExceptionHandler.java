package li.ma.mchart.controller.base;

import io.jsonwebtoken.SignatureException;
import li.ma.mchart.common.exception.BizException;
import li.ma.mchart.controller.entity.RemoteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: mario
 * @Date: 2018-09-30 1:19 PM
 * @Description:
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public RemoteResult handleBizException(BizException e) {
        log.warn(e.getMessage() + "[" + e.getLogMsg() + "]");
        return RemoteResult.failed(true, e.getMessage());
    }

    /**
     * 参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public RemoteResult handleBindException(BindException e) {
        log.warn(e.getMessage());
        return RemoteResult.failed(true, e.getFieldError().getDefaultMessage());
    }

    /**
     * 服务器其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RemoteResult handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return RemoteResult.failed(false, e.getMessage());
    }

    /**
     * 鉴权不通过
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SignatureException.class)
    public RemoteResult handlerUnauthorized(SignatureException e) {
        log.warn(e.getMessage());
        return RemoteResult.unauthorized();
    }
}
