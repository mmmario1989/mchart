package li.ma.mchart.dao;

import li.ma.mchart.dao.entity.Charter;
import li.ma.mchart.dao.entity.Group;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRespositoryTest {

    @Autowired
    GroupRepository repository;



    Group group;

    @Before
    public void init() {
        group = new Group();
        group.setAdmin("333");
        group.setName("group1");
        group.setNotice("123");
    }

    @Test
    public void save() {
        repository.save(group);
    }
    @Test
    public void find(){
        List<Group> list = (List<Group>) repository.findAll();
        System.out.println(123);
    }
    @Test
    public void relation(){
        Group group = repository.findById(3).get();
        Charter charter=group.getCharters().iterator().next();
        charter.setNickname("nnnnnnn");

        repository.save(group);
    }
}
