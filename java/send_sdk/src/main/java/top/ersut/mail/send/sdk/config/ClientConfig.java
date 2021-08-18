package top.ersut.mail.send.sdk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import top.ersut.mail.send.sdk.config.JacksonConfig;

import java.util.Objects;

public class ClientConfig {

    @Getter
    private WebClient webClient;

    public ClientConfig(String host,String key){
        Objects.requireNonNull(host,"host不能为空");
        Objects.requireNonNull(key,"key不能为空");

        ObjectMapper objectMapper = new JacksonConfig().objectMapper();

        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                }).build();

        webClient = WebClient.builder().exchangeStrategies(strategies).defaultHeader("key",key).baseUrl(host).build();
    }
}
