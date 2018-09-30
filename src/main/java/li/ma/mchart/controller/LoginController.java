package li.ma.mchart.controller;

import com.alibaba.fastjson.JSON;
import li.ma.mchart.biz.LoginBiz;
import li.ma.mchart.common.exception.BizException;
import li.ma.mchart.controller.entity.RegistInfo;
import li.ma.mchart.dao.entity.Charter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@RestController("/")
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


}
