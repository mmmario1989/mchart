package li.ma.mchat.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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
@Table(name = "t_chatter")
public class Chatter extends BaseEntity {

    private String account;
    private String password;
    private String nickname;

    @ManyToMany(mappedBy = "chatters",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Group> groups = new HashSet<>();


}
