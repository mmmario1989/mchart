package li.ma.mchart.controller;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import li.ma.mchart.biz.LoginBiz;
import li.ma.mchart.common.Constant;
import li.ma.mchart.common.exception.BizException;
import li.ma.mchart.controller.entity.RegistInfo;
import li.ma.mchart.dao.entity.Charter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginBiz loginBiz;

    @PostMapping("/regist")
    public Object regist(@Valid RegistInfo info) throws BizException {
        if(!info.getPwd().equals(info.getRePwd())){
            throw new BizException("重复密码错误", JSON.toJSONString(info));
        }
        Charter charter = new Charter();
        charter.setAccount(info.getAccount());
        charter.setPassword(info.getPwd());
        charter.setNickname(info.getNickName());
        loginBiz.regist(charter);
        return null;
    }

    @PostMapping("/login")
    public Object login(String account,String pwd,String imei) throws BizException {
        if(account==null || account.trim().isEmpty()){
            throw new BizException("账号不能为空");
        }
        if(pwd==null || pwd.trim().isEmpty()){
            throw new BizException("密码不能为空");
        }

        Charter charter =loginBiz.login(account,pwd);
        return Jwts.builder()
                .claim("charterId",charter.getId())
                .claim("account",charter.getAccount())
                .claim("nickname",charter.getNickname())
                .claim("imei",imei)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, Constant.SECRET_KEY)
                .compact();
    }



}
