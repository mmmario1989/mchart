package li.ma.mchart.biz;

import li.ma.mchart.dao.entity.Charter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: mario
 * @Date: 2018-10-17 2:32 PM
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class CharterBizTest {
    @Autowired
    private GroupBiz charterBiz;
    @Test
    public void test(){
        Charter charter1 =charterBiz.findById("ying");
        Charter charter2 = charterBiz.findById("ying");
        System.out.println();
    }
}
