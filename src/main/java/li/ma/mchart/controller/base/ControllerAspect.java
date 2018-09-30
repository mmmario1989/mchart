package li.ma.mchart.controller.base;

import li.ma.mchart.controller.entity.RemoteResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author: mario
 * @Date: 2018-09-30 1:48 PM
 * @Description:
 */
@Aspect
@Component
public class ControllerAspect {


    @Around("execution(* li.ma.mchart.controller.*.*(..))")
    public RemoteResult remoteResultPoint(ProceedingJoinPoint point) throws Throwable {
            return RemoteResult.succeed(point.proceed());
    }


}
