package li.ma.mchart.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
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

    private static void set(Integer charterId,String account,String nickname,String imei){
        LoginContext loginContext = new LoginContext();
        loginContext.setCharterId(charterId);
        loginContext.setAccount(account);
        loginContext.setNickname(nickname);
        loginContext.setImei(imei);
        context.set(loginContext);
    }

    public static void authorize(String token){
        if(token==null){
            throw new SignatureException("null token");
        }
        Jwt jwt = Jwts.parser().setSigningKey(Constant.SECRET_KEY).parse(token);
        Claims claims = (Claims) jwt.getBody();
        set(
                claims.get("charterId",Integer.class),
                claims.get("account",String.class),
                claims.get("nickname",String.class),
                claims.get("imei",String.class)
        );
    }
}
