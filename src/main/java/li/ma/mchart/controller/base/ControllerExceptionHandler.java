package li.ma.mchart.controller.base;

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


    @ExceptionHandler({BizException.class, BindException.class})
    public RemoteResult handleBizException(Exception e){
        String msg = null;
        if(e instanceof BizException){
            log.warn(e.getMessage()+"["+((BizException)e).getLogMsg()+"]");
            msg = e.getMessage();
        }else if(e instanceof BindException){
            log.warn(e.getMessage());
            msg = ((BindException) e).getFieldError().getDefaultMessage();
        }
        return RemoteResult.failed(true,msg);
    }

    @ExceptionHandler(Exception.class)
    public RemoteResult handlerException(Exception e){
        log.error(e.getMessage(),e);
        return RemoteResult.failed(false,e.getMessage());
    }
}
