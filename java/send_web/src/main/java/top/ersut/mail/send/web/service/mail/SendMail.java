package top.ersut.mail.send.web.service.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import top.ersut.mail.send.web.config.MailConfig;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author ersut
 */
@Service
@Slf4j
public class SendMail {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailConfig mailConfig;

    /**
     * 发送简单邮件
     * @param title 标题
     * @param recipientMail 收件人邮箱地址
     * @param content 内容
     * @return 是否发送成功
     */
    public boolean sendSimpleMail(String title,String content,String recipientMail){
        try {
            MimeMessage mimeMessage = mailConfig.getMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setSubject(title);
            message.setTo(recipientMail);
            message.setText(content);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            log.error("content:["+content+"] send error",e);
            return false;
        }
    }

}
