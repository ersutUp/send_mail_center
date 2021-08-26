package top.ersut.mail.send.sdk;

import org.junit.jupiter.api.*;
import top.ersut.mail.send.model.common.result.Result;
import top.ersut.mail.send.model.common.result.code.SystemResultCode;
import top.ersut.mail.send.model.v1.mail.param.SendMailParam;
import top.ersut.mail.send.model.andro.v1.mail.result.GetMailByIdResult;
import top.ersut.mail.send.model.v1.mail.result.SendMailResult;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientUtilTest {

    ClientUtil clientUtil = new ClientUtil("http://127.0.0.1:8080","Smf7g60uDvpzwSPHGE2v");

    private static Long mailRecordId;

    @Order(1)
    @Test
    void sendSimpleMailV1() throws IOException {
        Result<SendMailResult> result = clientUtil.sendSimpleMailV1(SendMailParam
                .builder()
                .title("android-sdk测试")
                .content("这是一封由android-sdk发送的一封邮件，时间戳："+System.nanoTime())
                .signId(1L)
                .build());
        System.out.println(result.toString());
        Assertions.assertEquals(result.getCode(), SystemResultCode.SUCCESS.getCode());
        mailRecordId = result.getData().getMailRecordId();
    }

    @Order(2)
    @Test
    void getMailByIdV1() throws IOException {
        Result<GetMailByIdResult> result = clientUtil.getMailByIdV1(mailRecordId == null ? 43L : mailRecordId);
        System.out.println(result.toString());
        Assertions.assertEquals(result.getCode(), SystemResultCode.SUCCESS.getCode());
        Assertions.assertNotNull(result.getData());
    }
}