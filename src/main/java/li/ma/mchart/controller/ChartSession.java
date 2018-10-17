package li.ma.mchart.controller;

import com.alibaba.fastjson.JSON;
import li.ma.mchart.biz.GroupBiz;
import li.ma.mchart.common.Constant;
import li.ma.mchart.common.LoginContext;
import li.ma.mchart.common.exception.BizException;
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

    private Integer charterId;
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
        this.charterId = LoginContext.get().getCharterId();
        this.session = session;
        this.nickname = LoginContext.get().getNickname();
        this.account = LoginContext.get().getAccount();
        this.imei = LoginContext.get().getImei();

        Charter charter = context.getBean(CharterRepository.class).findByAccount(this.account);
        SessionManager.join(charter.getGroups(),this);
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
        Charter charter = context.getBean(CharterRepository.class).findByAccount(this.account);
        SessionManager.leave(charter.getGroups(),this);
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
         * key=charterId
         * value=ChartSession
         */
        private static Map<Integer, ChartSession> sessionMap = new HashMap<>();

        private static synchronized void join(Collection<Group> groups,ChartSession session) throws BizException {
            for(Group group:groups){
                ChartMessage message = new ChartMessage();
                message.setData(session.getNickname()+" is coming!");
                message.setFromAccount(Constant.SERVER);
                message.setFromNickname(Constant.SERVER);
                message.setToGroupId(group.getId());
                message.setTime(new Date().getTime());
                dispatch(message);
            }
            if(sessionMap.containsKey(session.getCharterId())){
                throw new BizException("该用户已连接");
            }
            sessionMap.put(session.getCharterId(), session);
            log.info("===>"+session.getAccount()+"---"+session.getNickname());
        }


        private static synchronized void leave(Collection<Group> groups, ChartSession session) {
            sessionMap.remove(session.getCharterId());
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
            for(Charter charter:group.getCharters()){
                ChartSession session =sessionMap.get(charter.getId());
                if(session!=null){
                    session.receive(msg);
                }
            }

        }
    }
}
