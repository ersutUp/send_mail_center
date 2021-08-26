package top.ersut.mail.send.sdk;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import top.ersut.mail.send.model.common.result.Result;
import top.ersut.mail.send.model.v1.mail.param.SendMailParam;
import top.ersut.mail.send.model.v1.mail.result.GetMailByIdResult;
import top.ersut.mail.send.model.v1.mail.result.SendMailResult;
import top.ersut.mail.send.sdk.config.ClientConfig;

import java.time.Duration;

public class ClientUtil {
    private WebClient webClient;
    private final Duration timeout = Duration.ofSeconds(5);

    public ClientUtil(String host, String key){
        webClient = new ClientConfig(host,key).getWebClient();
    }


    private final String sendSimpleMailV1 = "/v1/mail/sendSimpleMail";
    /**
     * 发送简单邮件
     * @param sendMailParam
     * @return
     */
    public Result<SendMailResult> sendSimpleMailV1(SendMailParam sendMailParam){
        return webClient
                .post()
                .uri(sendSimpleMailV1)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(sendMailParam)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<SendMailResult>>(){})
                .block(timeout);
    }

    private final String getMailByIdV1 = "/v1/mail/{id}";
    /**
     * 根据id获取邮件信息
     * @param id
     * @return
     */
    public Result<GetMailByIdResult> getMailByIdV1(Long id){
        return webClient
                .get()
                .uri(getMailByIdV1,id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<GetMailByIdResult>>(){})
                .block(timeout);
    }

}
