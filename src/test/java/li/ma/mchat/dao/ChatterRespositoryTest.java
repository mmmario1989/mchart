package li.ma.mchat.dao;

import li.ma.mchat.dao.entity.Chatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatterRespositoryTest {

    @Autowired
    ChatterRepository repository;
    @Autowired
    GroupRepository groupRepository;
    Chatter chatter;
    @Before
    public void init() {
        chatter = new Chatter();
        chatter.setAccount("777");
        chatter.setNickname("tester");
        chatter.setPassword("123");
    }

    @Test
    public void save() {
        repository.save(chatter);
    }
    @Test
    public void find(){

//        List<ChatterSession> list = (List<ChatterSession>) repository.findAll();
//        System.out.println(123);

        Chatter chatter = repository.findByAccount("ying");
        repository.findByAccount("ying");
        System.out.println();
    }




}