package li.ma.mchart.controller.entity;

import lombok.Data;

@Data
public class RemoteResult<T> {

    private int code;
    private String msg;
    private T data;

    private RemoteResult(){};


    public static<T> RemoteResult<T> succeed(T data){
        RemoteResult<T> res = new RemoteResult<>();
        res.code=200;
        res.msg="success";
        res.data=data;
        return res;
    }

    public static <T> RemoteResult<T> failed(int code,String msg){
        RemoteResult<T> res = new RemoteResult<>();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }


}
