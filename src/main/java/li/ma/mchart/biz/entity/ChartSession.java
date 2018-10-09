package li.ma.mchart.biz.entity;

import li.ma.mchart.dao.entity.Charter;
import li.ma.mchart.dao.entity.Group;
import lombok.Builder;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;

/**
 * @Author: mario
 * @Date: 2018-10-09 5:26 PM
 * @Description:
 */
@Builder
public class ChartSession {

    private Charter charter;
    private Group group;
    private WebSocketSession socketSession;
    private String imei;


    public Integer getGroupId(){
        return group.getId();
    }

    public String getSessionId(){
        return group.getId()+"_"+charter.getId()+"_"+imei;
    }

    public void receive(WebSocketMessage message) throws IOException {
        socketSession.sendMessage(message);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChartSession that = (ChartSession) o;
        return Objects.equals(this.getSessionId(),that.getSessionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSessionId());
    }
}
