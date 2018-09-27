package li.ma.mchart.enums;

import li.ma.mchart.main.Charter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum  CharterGroup {

    GROUP1("测试组",new ArrayList<>());

    private String name;

    private List<CharterEnums> charterList;

    public void join(Charter charter) throws IOException {
        charterList.add(CharterEnums.getCharter(charter.getAccount()));
        for (CharterEnums charterE : charterList) {
            charterE.receive(new TextMessage("欢迎 "+charterE.getNickname()+" 加入！"));
        }
    }


}
