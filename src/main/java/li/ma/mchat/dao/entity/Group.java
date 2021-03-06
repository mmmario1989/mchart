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
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "t_group")
public class Group extends BaseEntity {

    private String name;
    private String notice;
    private String admin;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_group_chatter",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "chatter_id")}
    )
    private Set<Chatter> chatters = new HashSet<>();
}
