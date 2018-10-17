package li.ma.mchart.dao;

import li.ma.mchart.dao.entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@Repository
public interface GroupRepository extends CrudRepository<Group,Integer> {

}
