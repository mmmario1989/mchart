package li.ma.mchat.controller.entity;

import lombok.Data;
/**
 * @Author: mario
 * @Date: 2018-09-30 10:33 AM
 * @Description:
 */
@Data
public class RemoteResult<T> {


    private static final int SUCEED = 200;
    private static final int FAILED_BIZ = 501;
    private static final int FAILED_SERVER = 500;
    private static final int UNAUTHORIZED = 401;

    private int code;
    private String msg;
    private T data;

    private RemoteResult(){};


    public static<T> RemoteResult<T> succeed(T data){
        RemoteResult<T> res = new RemoteResult<>();
        res.code=SUCEED;
        res.msg="success";
        res.data=data;
        return res;
    }

    public static <T> RemoteResult<T> failed(boolean biz,String msg){
        RemoteResult<T> res = new RemoteResult<>();
        res.setCode(biz?FAILED_BIZ:FAILED_SERVER);
        res.setMsg(biz?msg:"内部错误，请稍后再试...");
        return res;
    }

    public static <T> RemoteResult<T> unauthorized(){
        RemoteResult<T> res = new RemoteResult<>();
        res.setMsg("请先登陆");
        res.setCode(UNAUTHORIZED);
        return res;
    }


}
