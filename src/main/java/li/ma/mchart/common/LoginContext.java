package li.ma.mchart.common;

import lombok.Data;

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
    private String imei;

    public static LoginContext get(){
        return context.get();
    }

    public static void clear(){
        context.remove();
    }

    public static void set(Integer charterId,String account,String nickname,String imei){
        LoginContext loginContext = new LoginContext();
        loginContext.setCharterId(charterId);
        loginContext.setAccount(account);
        loginContext.setNickname(nickname);
        loginContext.setImei(imei);
        context.set(loginContext);
    }
}
