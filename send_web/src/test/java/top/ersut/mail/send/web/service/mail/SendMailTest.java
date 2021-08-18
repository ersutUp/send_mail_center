package top.ersut.mail.send.web.service.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SendMailTest {

    @Autowired
    private SendMail sendMail;

    @Test
    void sendSimpleMailTest() {
        sendMail.sendSimpleMail("测试","83889573@qq.com","这是一封测试邮件<br><p style=\"color:red\">这是html内容</p><br>发送时间："+ LocalDateTime.now());
    }
}