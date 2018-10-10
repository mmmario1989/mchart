package li.ma.mchart.service;

import li.ma.mchart.biz.SessionManager;
import li.ma.mchart.biz.entity.ChartSession;
import li.ma.mchart.common.LoginContext;
import li.ma.mchart.dao.CharterRepository;
import li.ma.mchart.dao.GroupRepository;
import li.ma.mchart.dao.entity.Charter;
import li.ma.mchart.dao.entity.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@ServerEndpoint("/webSocket/{groupId}")
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets=new CopyOnWriteArraySet<>();

    @Resource
    private SessionManager sessionManager;

    @Resource
    private CharterRepository charterRepository;

    @Resource
    private GroupRepository groupRepository;

    @OnOpen
    public void onOpen(@PathParam("groupId") Integer groupId, Session session){
        this.session=session;
        webSockets.add(this);
        log.info("有新的连接，总数"+webSockets.size());
        if (LoginContext.get()==null){
            return;
        }
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

    @OnClose
    public void onClose(){
        webSockets.remove(this);
        System.out.println("有新的断开，总数"+webSockets.size());
    }


    @OnMessage
    public void onMessage(@PathParam("groupId") Integer groupId,String message){
        send(message);
        if (sessionManager!=null){
            sessionManager.dispatch(groupId,message);
        }
    }

    public void send(String message){
        for (WebSocket webSocket:webSockets){
            try {
                webSocket.session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
