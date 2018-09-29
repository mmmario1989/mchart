package li.ma.mchart.common.enums;

import li.ma.mchart.biz.CharterSession;
import lombok.Getter;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


@Getter
public enum CharterEnums implements CharterSession {

    C1("test","123","tester", CharterGroup.GROUP1),
    C2("test1","123","tester1",CharterGroup.GROUP1);

    private String account;
    private String password;
    private String nickname;
    private CharterGroup group;
    private WebSocketSession session;

    CharterEnums(String account, String password, String nickname, CharterGroup group) {
        this.account = account;
        this.password = password;
        this.nickname = nickname;
        this.group = group;
    }

    public void setSession(WebSocketSession session){
        this.session = session;
    }

    @Override
    public void receive(WebSocketMessage<?> msg) throws IOException {
        session.sendMessage(msg);
    }

    public static CharterEnums getCharter(String account){
        for(CharterEnums charter: CharterEnums.values()){
            if(charter.getAccount().equals(account)){
                return charter;
            }
        }
        return null;
    }

}

