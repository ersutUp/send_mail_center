package top.ersut.mail.send.model.common.result.code;

import lombok.Getter;

public enum SystemResultCode implements ResultCode {

    SUCCESS("EM-0",""),
    UNKNOWN_ERROR("EM-1001","未知错误"),
    SEND_MAIL_FAIL("EM-1002","发送邮件失败"),
    PARAM_ERROR("EM-1003","参数错误"),
    APP_NOT_EXIST("EM-1004","应用不存在"),
    SIGN_NOT_EXIST("EM-1004","签名不存在"),
    SIGN_UNAUTHORIZED("EM-1005","签名未授权"),
    DATA_NOT_FIND("EM-1006","数据不存在"),
    SYSTEM_ERROR("EM-1007","系统错误"),
    ;
    @Getter
    String code;
    @Getter
    String msg;

    SystemResultCode(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
