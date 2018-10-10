package li.ma.mchart.controller;

import li.ma.mchart.biz.SessionManager;
import li.ma.mchart.biz.entity.ChartSession;
import li.ma.mchart.common.LoginContext;
import li.ma.mchart.dao.CharterRepository;
import li.ma.mchart.dao.GroupRepository;
import li.ma.mchart.dao.entity.Charter;
import li.ma.mchart.dao.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@ServerEndpoint("/connect/{groupId}")
@Component
public class ConnectController {
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private CharterRepository charterRepository;
    @Autowired
    private GroupRepository groupRepository;


    @OnOpen
    public void afterConnectionEstablished(@PathParam("groupId") Integer groupId, Session session) throws Exception {
//        Integer groupId = Integer.valueOf( (String) session.getAttributes().get("groupId"));
        Charter charter = charterRepository.findByAccount(LoginContext.get().getAccount());
        Group group = groupRepository.findById(groupId).get();
        sessionManager.put(
                ChartSession.builder()
                .charter(charter)
                .group(group)
                .session(session)
                .imei(LoginContext.get().getImei())
                .build()
        );
    }

    @OnMessage
    public void handleMessage(@PathParam("groupId") Integer groupId,String message) throws Exception {
//        Integer groupId = Integer.valueOf( (String) session.getAttributes().get("groupId"));
        sessionManager.dispatch(groupId,message);
    }

    @OnError
    public void handleTransportError(@PathParam("groupId") Integer groupId, Throwable exception) throws Exception {
//        Integer groupId = Integer.valueOf( (String) session.getAttributes().get("groupId"));
        sessionManager.remove(groupId);
    }

    @OnClose
    public void afterConnectionClosed(@PathParam("groupId") Integer groupId) throws Exception {
//        Integer groupId = Integer.valueOf( (String) session.getAttributes().get("groupId"));
        sessionManager.remove(groupId);
    }


}
