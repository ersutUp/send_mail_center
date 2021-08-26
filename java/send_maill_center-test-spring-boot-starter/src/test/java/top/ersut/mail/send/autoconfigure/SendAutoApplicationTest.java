package top.ersut.mail.send.autoconfigure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ersut.mail.send.model.common.result.Result;
import top.ersut.mail.send.model.common.result.code.SystemResultCode;
import top.ersut.mail.send.model.v1.mail.param.SendMailParam;
import top.ersut.mail.send.model.v1.mail.result.SendMailResult;
import top.ersut.mail.send.sdk.ClientUtil;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SendAutoApplicationTest {


    @Autowired
    private ClientUtil clientUtil;

    @Test
    public void testSend(){
        Result<SendMailResult> result = clientUtil.sendSimpleMailV1(SendMailParam
                .builder()
                .title("start测试")
                .content("这是一封由start-sdk发送的一封邮件，时间戳："+System.nanoTime())
                .signId(1L)
                .build());
        System.out.println(result.toString());
        Assertions.assertEquals(result.getCode(), SystemResultCode.SUCCESS.getCode());
    }


}