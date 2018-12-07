package li.ma.mchat.biz;

import li.ma.mchat.dao.ChatterRepository;
import li.ma.mchat.dao.GroupRepository;
import li.ma.mchat.dao.entity.Chatter;
import li.ma.mchat.dao.entity.Group;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @Author: mario
 * @Date: 2018-10-17 2:30 PM
 * @Description:
 */
@Service
public class GroupBiz {

    @Resource
    private GroupRepository groupRepository;
    @Resource
    private ChatterRepository chatterRepository;

    @Cacheable(value = "group",key = "#id")
    public Group findById(Integer id){
        return groupRepository.findById(id).get();
    }

    @Transactional
    @CacheEvict(value = "group",key = "#groupId")
    public void addChatter(Integer groupId,Integer chatterId){
        Chatter chatter = chatterRepository.findById(chatterId).get();
        Group group = groupRepository.findById(groupId).get();
        group.getChatters().add(chatter);
        groupRepository.save(group);
    }


    @CacheEvict(value = "group",key = "#groupId")
    @Transactional
    public void delChatter(Integer groupId, Integer chatterId){
        Group group = groupRepository.findById(groupId).get();
        if(group.getChatters().isEmpty()){
            return;
        }
        group.getChatters().removeIf(chatter -> chatter.getId().equals(chatterId));
        groupRepository.save(group);
    }
}
