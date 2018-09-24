package li.ma.mchart.main;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  Charter {

    C1("test","test","tester",CharterGroup.GROUP1),
    C2("test1","test1","tester1",CharterGroup.GROUP1);

    private String account;
    private String password;
    private String nickname;
    private CharterGroup group;


}

