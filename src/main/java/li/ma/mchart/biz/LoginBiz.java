package li.ma.mchart.biz;

import li.ma.mchart.common.exception.BizException;
import li.ma.mchart.dao.CharterRepository;
import li.ma.mchart.dao.entity.Charter;
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
    private CharterRepository charterRepository;

    public void regist(Charter charter) throws BizException {
        if(charterRepository.existsByAccount(charter.getAccount())){
            throw new BizException("账号已存在");
        }
        charterRepository.save(charter);
    }



}
