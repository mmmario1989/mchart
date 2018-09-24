package li.ma.mchart.main;

import org.springframework.web.socket.WebSocketSession;

import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;

@ServerEndpoint("/chart")
public class CharterManager {

    private Map<Integer,CharterGroup> groupMap;


    @OnOpen
    public void onJoin(WebSocketSession session){

    }



}
