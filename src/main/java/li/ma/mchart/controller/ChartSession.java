package li.ma.mchart.controller;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import li.ma.mchart.common.LoginContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;

/**
 * @Author: mario
 * @Date: 2018-10-09 5:26 PM
 * @Description:
 */

@Getter
@ServerEndpoint("/connect/{groupId}/{token}")
@Component
public class ChartSession {

    private Integer charterId;
    private Integer groupId;
    private String imei;
    private String nickname;
    private String account;
    private Session session;


    public String getSessionId() {
        return groupId + "_" + charterId + "_" + imei;
    }

    public void receive(String message) throws IOException {
        session.getAsyncRemote().sendText(message);
    }


    @OnOpen
    public void onOpen(@PathParam("groupId") Integer groupId, @PathParam("token") String token, Session session) throws Exception {
        LoginContext.authorize(token);
        this.charterId = LoginContext.get().getCharterId();
        this.groupId = groupId;
        this.session = session;
        this.nickname = LoginContext.get().getNickname();
        this.account = LoginContext.get().getAccount();
        this.imei = LoginContext.get().getImei();
        SessionManager.put(this);
    }

    @OnMessage
    public void onMessage(@PathParam("groupId") Integer groupId, String message) throws Exception {
        SessionManager.dispatch(groupId, this.nickname+" : "+message);
    }

    @OnError
    public void onError(Throwable e) throws Exception {
        SessionManager.remove(this);
    }

    @OnClose
    public void onClose() throws Exception {
        SessionManager.remove(this);
    }


    @Slf4j
    private static class SessionManager {

        private static Multimap<Integer, ChartSession> sessionMap = HashMultimap.create();

        private static synchronized void put(ChartSession session) {
            sessionMap.put(session.getGroupId(), session);
            dispatch(session.getGroupId(),"Welcome "+session.getNickname()+"!");
            log.info("===>"+session.getNickname()+"---"+session.getGroupId());
        }


        private static synchronized void remove(ChartSession session) {
            Collection<ChartSession> sessions = sessionMap.get(session.getGroupId());
            if (sessions == null) {
                return;
            }
            sessions.remove(session);
            log.info(session.getNickname()+"---"+session.getGroupId()+"===>");
        }

        private static void dispatch(Integer groupId, String msg) {
            Collection<ChartSession> sessions = sessionMap.get(groupId);
            for (ChartSession session : sessions) {
                try {
                    session.receive(msg);
                } catch (IOException e) {
                    log.error("send to " + session.getSessionId() + " error", e);
                }
            }
        }
    }
}
