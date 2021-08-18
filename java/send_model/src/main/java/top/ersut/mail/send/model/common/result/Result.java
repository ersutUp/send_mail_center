package top.ersut.mail.send.model.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.ersut.mail.send.model.common.result.code.ResultCode;
import top.ersut.mail.send.model.common.result.code.SystemResultCode;

@Data
public class Result<T> {

    String code;
    String msg;
    //null时不进行序列化
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    public Result(){}

    public Result(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public Result(ResultCode resultCode,String msg){
        this.code = resultCode.getCode();
        this.msg = msg;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public final static Result SUCCESS = new Result(SystemResultCode.SUCCESS);

}
