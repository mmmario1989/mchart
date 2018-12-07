package li.ma.mchat.controller;

import com.alibaba.fastjson.JSON;
import li.ma.mchat.biz.GroupBiz;
import li.ma.mchat.common.Constant;
import li.ma.mchat.common.LoginContext;
import li.ma.mchat.common.exception.BizException;
import li.ma.mchat.controller.entity.ChartMessage;
import li.ma.mchat.dao.ChatterRepository;
import li.ma.mchat.dao.entity.Chatter;
import li.ma.mchat.dao.entity.Group;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private Integer chatterId;
    private String imei;
    private String nickname;
    private String account;
    private Session session;

    private static ApplicationContext context;

    public void receive(ChartMessage message) {
        session.getAsyncRemote().sendText(JSON.toJSONString(message));
    }


    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session) throws Exception {
        LoginContext.authorize(token);
        this.chatterId = LoginContext.get().getChatterId();
        this.session = session;
        this.nickname = LoginContext.get().getNickname();
        this.account = LoginContext.get().getAccount();
        this.imei = LoginContext.get().getImei();

        Chatter chatter = context.getBean(ChatterRepository.class).findByAccount(this.account);
        SessionManager.join(chatter.getGroups(),this);
    }

    @OnMessage
    public void onMessage(String message) {
        ChartMessage chartMessage = JSON.parseObject(message,ChartMessage.class);
        SessionManager.dispatch(chartMessage);
    }

    @OnError
    public void onError(Throwable e)  {
        log.error(this.getAccount()+" chart session error",e);
    }

    @OnClose
    public void onClose()  {
        Chatter chatter = context.getBean(ChatterRepository.class).findByAccount(this.account);
        SessionManager.leave(chatter.getGroups(),this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }


    /**
     * 管理会话，分发消息
     */
    @Slf4j
    private static class SessionManager {

        /**
         * key=chatterId
         * value=ChartSession
         */
        private static Map<Integer, ChartSession> sessionMap = new HashMap<>();

        private static synchronized void join(Collection<Group> groups,ChartSession session) throws BizException {
            if(sessionMap.containsKey(session.getChatterId())){
                throw new BizException("该用户已连接");
            }
            for(Group group:groups){
                ChartMessage message = new ChartMessage();
                message.setData(session.getNickname()+" is coming!");
                message.setFromAccount(Constant.SERVER);
                message.setFromNickname(Constant.SERVER);
                message.setToGroupId(group.getId());
                message.setTime(new Date().getTime());
                dispatch(message);
            }
            sessionMap.put(session.getChatterId(), session);
            log.info("===>"+session.getAccount()+"---"+session.getNickname());
        }


        private static synchronized void leave(Collection<Group> groups, ChartSession session) {
            if(sessionMap.get(session.getChatterId())!=session){
                return;
            }
            sessionMap.remove(session.getChatterId());
            for(Group group:groups){
                ChartMessage message = new ChartMessage();
                message.setData(session.getNickname()+" is leaving!");
                message.setFromAccount(Constant.SERVER);
                message.setFromNickname(Constant.SERVER);
                message.setToGroupId(group.getId());
                message.setTime(new Date().getTime());
                dispatch(message);
            }
            log.info(session.getAccount()+"---"+session.getNickname()+"===>");
        }

        private static void dispatch(ChartMessage msg) {
            Group group = context.getBean(GroupBiz.class).findById(msg.getToGroupId());
            for(Chatter chatter :group.getChatters()){
                ChartSession session =sessionMap.get(chatter.getId());
                if(session!=null){
                    session.receive(msg);
                }
            }

        }
    }
}
