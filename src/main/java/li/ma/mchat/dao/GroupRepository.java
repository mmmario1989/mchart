package li.ma.mchat.dao;

import li.ma.mchat.dao.entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@Repository
public interface GroupRepository extends CrudRepository<Group,Integer> {

}
