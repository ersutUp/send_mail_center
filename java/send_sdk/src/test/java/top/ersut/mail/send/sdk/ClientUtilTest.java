package top.ersut.mail.send.sdk;


import org.junit.jupiter.api.*;
import top.ersut.mail.send.model.common.result.Result;
import top.ersut.mail.send.model.common.result.code.SystemResultCode;
import top.ersut.mail.send.model.v1.mail.param.SendMailParam;
import top.ersut.mail.send.model.v1.mail.result.GetMailByIdResult;
import top.ersut.mail.send.model.v1.mail.result.SendMailResult;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientUtilTest {

    private static ClientUtil clientUtil;

    private static Long mailRecordId;

    @BeforeAll
    static void beafor(){
        clientUtil = new ClientUtil("http://127.0.0.1:8080","Smf7g60uDvpzwSPHGE2v");
    }

    @Test
    @Order(1)
    void sendSimpleMailV1() {
        Result<SendMailResult> result = clientUtil.sendSimpleMailV1(SendMailParam
                .builder()
                .title("sdk测试")
                .content("这是一封由sdk发送的一封邮件，时间戳："+System.nanoTime())
                .signId(1L)
                .build());
        System.out.println(result.toString());
        Assertions.assertEquals(result.getCode(), SystemResultCode.SUCCESS.getCode());
        mailRecordId = result.getData().getMailRecordId();
    }

    @Test
    @Order(2)
    void getMailByIdV1() {
        Result<GetMailByIdResult> result = clientUtil.getMailByIdV1(mailRecordId == null ? 43L : mailRecordId);
        System.out.println(result.toString());
        Assertions.assertEquals(result.getCode(), SystemResultCode.SUCCESS.getCode());
        Assertions.assertNotNull(result.getData());
    }
}