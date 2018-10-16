package li.ma.mchart.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import li.ma.mchart.common.Constant;
import li.ma.mchart.common.LoginContext;
import li.ma.mchart.controller.entity.ChartMessage;
import li.ma.mchart.dao.CharterRepository;
import li.ma.mchart.dao.entity.Charter;
import li.ma.mchart.dao.entity.Group;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

/**
 * @Author: mario
 * @Date: 2018-10-09 5:26 PM
 * @Description:
 */
@Slf4j
@Getter
@ServerEndpoint("/connect/{token}")
@Component
public class ChartSession implements ApplicationContextAware {

    private Integer charterId;
    private String imei;
    private String nickname;
    private String account;
    private Session session;
    private Charter charter;

    private static ApplicationContext context;

    public void receive(ChartMessage message) throws IOException {
        session.getAsyncRemote().sendText(JSON.toJSONString(message));
    }


    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session) throws Exception {
        LoginContext.authorize(token);
        this.charterId = LoginContext.get().getCharterId();
        this.session = session;
        this.nickname = LoginContext.get().getNickname();
        this.account = LoginContext.get().getAccount();
        this.imei = LoginContext.get().getImei();

        Charter charter = context.getBean(CharterRepository.class).findByAccount(this.account);
        this.charter=charter;
        SessionManager.join(charter.getGroups(),this);
    }

    @OnMessage
    public void onMessage(String message) throws Exception {
        ChartMessage chartMessage = JSON.parseObject(message,ChartMessage.class);
        SessionManager.dispatch(chartMessage);
    }

    @OnError
    public void onError(Throwable e) throws Exception {
        log.error(this.getAccount()+" chart session error",e);
//        Charter charter = context.getBean(CharterRepository.class).findByAccount(this.account);
//        SessionManager.leave(charter.getGroups(),this);
    }

    @OnClose
    public void onClose() throws Exception {
        Charter charter = context.getBean(CharterRepository.class).findByAccount(this.account);
        SessionManager.leave(charter.getGroups(),this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }


    @Slf4j
    private static class SessionManager {

        private static Multimap<Integer, ChartSession> sessionMap = HashMultimap.create();

        private static synchronized void join(Collection<Group> groups,ChartSession session) {
            for(Group group:groups){
                ChartMessage message = new ChartMessage();
                message.setData(session.getNickname()+" is coming!");
                message.setFromAccount(Constant.SERVER);
                message.setFromNickname(Constant.SERVER);
                message.setToGroupId(group.getId());
                message.setTime(new Date().getTime());
                dispatch(message);
                sessionMap.put(group.getId(), session);
                log.info("===>"+session.getAccount()+"---"+session.getNickname());
            }
        }


        private static synchronized void leave(Collection<Group> groups, ChartSession session) {
            for(Group group:groups){
                Collection<ChartSession> sessions = sessionMap.get(group.getId());
                if (sessions == null) {
                    return;
                }
                sessions.remove(session);
                ChartMessage message = new ChartMessage();
                message.setData(session.getNickname()+" is leaving!");
                message.setFromAccount(Constant.SERVER);
                message.setFromNickname(Constant.SERVER);
                message.setToGroupId(group.getId());
                message.setTime(new Date().getTime());
                dispatch(message);
                log.info(session.getAccount()+"---"+session.getNickname()+"===>");
            }
        }

        private static void dispatch(ChartMessage msg) {
            Collection<ChartSession> sessions = sessionMap.get(msg.getToGroupId());
            for (ChartSession session : sessions) {
                try {
                    session.receive(msg);
                } catch (IOException e) {
                    log.error("send to " + session.getAccount() + " error", e);
                }
            }
        }
    }
}
