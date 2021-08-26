package top.ersut.mail.send.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.ersut.mail.send.sdk.ClientUtil;

@Configuration
@EnableConfigurationProperties(MailSendPropertie.class)
@ConditionalOnProperty(prefix = "mail-send", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MailSendAutoconfigure {

    @Bean
    public ClientUtil clientUtil(MailSendPropertie mailSendPropertie){
        return new ClientUtil(mailSendPropertie.getHost(), mailSendPropertie.getKey());
    }

}
