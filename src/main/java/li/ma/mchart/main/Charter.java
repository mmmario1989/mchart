package li.ma.mchart.main;

import org.springframework.web.socket.WebSocketMessage;

public interface Charter {

    void receive(WebSocketMessage<?> msg) throws Exception;

    String getAccount();
}
