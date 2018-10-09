package li.ma.mchart.biz;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import li.ma.mchart.biz.entity.ChartSession;
import li.ma.mchart.common.LoginContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * @Author: mario
 * @Date: 2018-10-09 5:36 PM
 * @Description:
 */
@Component
@Slf4j
public class SessionManager {

    private Multimap<Integer, ChartSession> sessionMap = HashMultimap.create();

    public synchronized void put(ChartSession session){
        sessionMap.put(session.getGroupId(),session);
    }

    public synchronized Collection<ChartSession> get(Integer groupId){
        return sessionMap.get(groupId);
    }

    public synchronized void remove(Integer groupId){
        Collection<ChartSession> sessionList = sessionMap.get(groupId);
        Iterator<ChartSession> iter = sessionList.iterator();
        String sessionId =groupId+"_"+LoginContext.get().getCharterId()+"_"+LoginContext.get().getImei();
        while (iter.hasNext()){
            if(iter.next().getSessionId().equals(sessionId)){
                iter.remove();
            }
        }
    }

    public void dispatch(Integer groupId,WebSocketMessage msg){
        Collection<ChartSession> sessions = sessionMap.get(groupId);
        for(ChartSession session:sessions){
            try {
                session.receive(msg);
            } catch (IOException e) {
                log.error("send to "+session.getSessionId()+" error",e);
            }
        }
    }
}
