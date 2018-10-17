package li.ma.mchart.biz;

import li.ma.mchart.dao.CharterRepository;
import li.ma.mchart.dao.GroupRepository;
import li.ma.mchart.dao.entity.Charter;
import li.ma.mchart.dao.entity.Group;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Iterator;

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
    private CharterRepository charterRepository;

    @Cacheable(value = "group",key = "#id")
    public Group findById(Integer id){
        return groupRepository.findById(id).get();
    }

    @Transactional
    @CacheEvict(value = "group",key = "#groupId")
    public void addCharter(Integer groupId,Integer charterId){
        Charter charter =charterRepository.findById(charterId).get();
        Group group = groupRepository.findById(groupId).get();
        group.getCharters().add(charter);
        groupRepository.save(group);
    }


    @CacheEvict(value = "group",key = "#groupId")
    @Transactional
    public void delCharter(Integer groupId,Integer charterId){
        Group group = groupRepository.findById(groupId).get();
        if(group.getCharters().isEmpty()){
            return;
        }
        group.getCharters().removeIf(charter -> charter.getId().equals(charterId));
        groupRepository.save(group);
    }
}
