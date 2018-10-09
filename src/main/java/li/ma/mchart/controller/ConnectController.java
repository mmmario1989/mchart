package li.ma.mchart.controller;

import li.ma.mchart.biz.SessionManager;
import li.ma.mchart.biz.entity.ChartSession;
import li.ma.mchart.common.LoginContext;
import li.ma.mchart.dao.CharterRepository;
import li.ma.mchart.dao.GroupRepository;
import li.ma.mchart.dao.entity.Charter;
import li.ma.mchart.dao.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.server.ServerEndpoint;

/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@ServerEndpoint("/connect")
public class ConnectController implements WebSocketHandler {
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private CharterRepository charterRepository;
    @Autowired
    private GroupRepository groupRepository;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Integer groupId = Integer.valueOf( (String) session.getAttributes().get("groupId"));
        Charter charter = charterRepository.findByAccount(LoginContext.get().getAccount());
        Group group = groupRepository.findById(groupId).get();
        sessionManager.put(
                ChartSession.builder()
                .charter(charter)
                .group(group)
                .socketSession(session)
                .imei(LoginContext.get().getImei())
                .build()
        );
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Integer groupId = Integer.valueOf( (String) session.getAttributes().get("groupId"));
        sessionManager.dispatch(groupId,message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        Integer groupId = Integer.valueOf( (String) session.getAttributes().get("groupId"));
        sessionManager.remove(groupId);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
