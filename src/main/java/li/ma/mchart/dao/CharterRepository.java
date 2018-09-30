package li.ma.mchart.dao;

import li.ma.mchart.dao.entity.Charter;
import org.springframework.data.repository.CrudRepository;
/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
public interface CharterRepository extends CrudRepository<Charter,Integer> {

    Charter findByAccount(String account);

    boolean existsByAccount(String account);
}
