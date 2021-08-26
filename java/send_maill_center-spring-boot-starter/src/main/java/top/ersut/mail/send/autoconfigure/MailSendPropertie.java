package top.ersut.mail.send.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mail-send")
@Data
public class MailSendPropertie {
    private boolean enabled;

    private String host;
    private String key;
}
