package li.ma.mchart.main;

import li.ma.mchart.enums.CharterEnums;
import org.springframework.web.socket.*;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chart")
public class CharterManager implements WebSocketHandler {


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String account = null;
        String password = null;
        CharterEnums charter = CharterEnums.getCharter(account);
        if(charter==null){
            session.sendMessage(new TextMessage("账号不存在！"));
            session.close();
        }else if(!charter.getPassword().equals(password)){
            session.sendMessage(new TextMessage("密码错误！"));
            session.close();
        }else {
            charter.setSession(session);
        }

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
