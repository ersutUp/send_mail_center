package top.ersut.mail.send.web.exception.code;


import lombok.Getter;
import top.ersut.mail.send.web.exception.base.BaseRuntimeException;
import top.ersut.mail.send.model.common.result.code.ResultCode;

public class CodeException extends BaseRuntimeException {
    @Getter
    private final ResultCode resultCode;

    public CodeException(ResultCode resultcode){
        super("错误消息["+resultcode.getMsg()+"]，对应状态码:["+resultcode.getCode()+"]");
        this.resultCode = resultcode;
    }
    public CodeException(ResultCode resultcode,Throwable throwable){
        super("错误消息["+resultcode.getMsg()+"]，对应状态码:["+resultcode.getCode()+"]",throwable);
        this.resultCode = resultcode;
    }

}
