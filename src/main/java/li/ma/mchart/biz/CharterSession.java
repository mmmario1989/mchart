package li.ma.mchart.biz;

import org.springframework.web.socket.WebSocketMessage;

/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
public interface CharterSession {

    void receive(WebSocketMessage<?> msg) throws Exception;

    String getAccount();
}
