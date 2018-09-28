package li.ma.mchart.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Charter extends BaseEntity {

    private String account;
    private String password;
    private String nickname;

}
