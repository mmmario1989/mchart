package li.ma.mchart.common;

import lombok.Data;

import java.util.Date;

/**
 * @Author: mario
 * @Date: 2018-09-30 11:08 PM
 * @Description:
 */
@Data
public class LoginContext {

    private static final ThreadLocal<LoginContext> context = new InheritableThreadLocal<>();

    private String account;
    private Integer charterId;
    private String nickname;

    public static LoginContext get(){
        return context.get();
    }

    public static void clear(){
        context.remove();
    }

    public static void set(Integer charterId,String account,String nickname){
        LoginContext loginContext = new LoginContext();
        loginContext.setCharterId(charterId);
        loginContext.setAccount(account);
        loginContext.setNickname(nickname);
        context.set(loginContext);
    }
}
