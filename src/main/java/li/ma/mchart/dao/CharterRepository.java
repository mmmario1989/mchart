package li.ma.mchart.dao;

import li.ma.mchart.dao.entity.Charter;
import org.springframework.data.repository.CrudRepository;

public interface CharterRepository extends CrudRepository<Charter,Integer> {

    Charter findByAccount(String account);

    boolean existsByAccount(String account);
}
