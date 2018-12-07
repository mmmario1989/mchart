package li.ma.mchat.biz;

import li.ma.mchat.common.exception.BizException;
import li.ma.mchat.dao.ChatterRepository;
import li.ma.mchat.dao.entity.Chatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@Service
public class LoginBiz {

    @Autowired
    private ChatterRepository chatterRepository;

    public void regist(Chatter chatter) throws BizException {
        if(chatterRepository.existsByAccount(chatter.getAccount())){
            throw new BizException("账号已存在","account:"+ chatter.getAccount());
        }
        chatterRepository.save(chatter);
    }


    public Chatter login(String account, String pwd) throws BizException {
        Chatter chatter = chatterRepository.findByAccount(account);
        if(chatter ==null){
            throw new BizException("账号不存在","account:"+account);
        }
        if(!chatter.getPassword().equals(pwd)){
            throw new BizException("密码错误","account:"+account);
        }
        return chatter;
    }



}
