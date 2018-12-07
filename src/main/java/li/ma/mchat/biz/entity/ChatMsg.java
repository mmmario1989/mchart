package li.ma.mchat.biz.entity;

import lombok.Data;

/**
 * @Author: mario
 * @Date: 2018-10-09 7:18 PM
 * @Description:
 */
@Data
public class ChatMsg {

    private String chatterId;
    private String nickname;
    private String msg;
}
