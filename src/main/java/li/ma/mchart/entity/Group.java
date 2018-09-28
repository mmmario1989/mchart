package li.ma.mchart.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
public class Group extends BaseEntity {

    private String name;
    private String notice;
    private String admin;
}
