package top.ersut.mail.send.sdk.config;

import lombok.Getter;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

public class ClientConfig {

    @Getter
    private OkHttpClient.Builder okHttpClientBuilder;

    private final String key;
    public ClientConfig(String key){
        super();
        Objects.requireNonNull(key,"key不能为空");

        this.key = key;

        okHttpClientBuilder = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(10))
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(5))
                .writeTimeout(Duration.ofSeconds(5))
                .addInterceptor(new ConfigInterceptor());

    }

    public OkHttpClient bulid(){
        return okHttpClientBuilder.build();
    }

    class ConfigInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.header("key",key);

            return chain.proceed(requestBuilder.build());
        }
    }

}
