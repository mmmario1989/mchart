package li.ma.mchart.dao;

import li.ma.mchart.dao.entity.Charter;
import li.ma.mchart.dao.entity.Group;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CharterRespositoryTest {

    @Autowired
    CharterRepository repository;
    @Autowired
    GroupRepository groupRepository;
    Charter charter;
    @Before
    public void init() {
        charter = new Charter();
        charter.setAccount("777");
        charter.setNickname("tester");
        charter.setPassword("123");
    }

    @Test
    public void save() {
        repository.save(charter);
    }
    @Test
    public void find(){

//        List<CharterSession> list = (List<CharterSession>) repository.findAll();
//        System.out.println(123);

        Charter charter = repository.findByAccount("ying");
        repository.findByAccount("ying");
        System.out.println();
    }




}