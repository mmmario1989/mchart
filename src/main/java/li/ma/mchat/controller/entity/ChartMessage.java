package li.ma.mchat.controller.entity;

import lombok.Data;

/**
 * @Author: mario
 * @Date: 2018-10-15 1:57 PM
 * @Description:
 */
@Data
public class ChartMessage {

    private String fromAccount;
    private String fromNickname;
    private Integer toGroupId;
    private String data;
    private Long time;
}
