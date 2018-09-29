package li.ma.mchart.biz;

import org.springframework.web.socket.WebSocketMessage;

public interface CharterSession {

    void receive(WebSocketMessage<?> msg) throws Exception;

    String getAccount();
}
