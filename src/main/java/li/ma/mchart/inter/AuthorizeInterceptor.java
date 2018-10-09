package li.ma.mchart.inter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import li.ma.mchart.common.Constant;
import li.ma.mchart.common.LoginContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: mario
 * @Date: 2018-09-30 5:54 PM
 * @Description:
 */
public class AuthorizeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization");
        if(token==null){
            throw new SignatureException("null token");
        }
        Jwt jwt = Jwts.parser().setSigningKey(Constant.SECRET_KEY).parse(token);
        Claims claims = (Claims) jwt.getBody();
        LoginContext.set(
                claims.get("charterId",Integer.class),
                claims.get("account",String.class),
                claims.get("nickname",String.class),
                claims.get("imei",String.class)
        );
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginContext.clear();
    }
}
