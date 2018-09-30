package li.ma.mchart.controller.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: mario
 * @Date: 2018-09-30 2:34 PM
 * @Description:
 */
@Data
public class RegistInfo {

    @NotBlank(message = "请输入账号")
    private String account;
    @NotBlank(message = "请输入密码")
    private String pwd;
    @NotBlank(message = "请输入重复密码")
    private String rePwd;
    @NotBlank(message = "请输入昵称")
    private String nickName;
}
