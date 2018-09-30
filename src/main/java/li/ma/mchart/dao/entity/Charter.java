package li.ma.mchart.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "t_charter")
public class Charter extends BaseEntity {

    private String account;
    private String password;
    private String nickname;

    @ManyToMany(mappedBy = "charters",cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>();

}
