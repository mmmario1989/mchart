package li.ma.mchat.biz;

import li.ma.mchat.dao.entity.Group;
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
public class GroupBizTest {
    @Autowired
    private GroupBiz groupBiz;
    @Test
    public void test(){
        Group group1 = groupBiz.findById(23);
        Group group2 = groupBiz.findById(23);
        System.out.println();
    }
}
