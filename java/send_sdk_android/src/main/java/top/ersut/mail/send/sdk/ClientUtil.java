package top.ersut.mail.send.sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import top.ersut.mail.send.model.common.result.Result;
import top.ersut.mail.send.model.v1.mail.param.SendMailParam;
import top.ersut.mail.send.model.andro.v1.mail.result.GetMailByIdResult;
import top.ersut.mail.send.model.v1.mail.result.SendMailResult;
import top.ersut.mail.send.sdk.config.ClientConfig;
import top.ersut.mail.send.sdk.config.GsonConfig;

import java.io.IOException;
import java.util.Objects;

public class ClientUtil {
    private OkHttpClient client;
    private final String HOST;

    private Gson gson = GsonConfig.gson;

    public ClientUtil(String host, String key){
        Objects.requireNonNull(host,"host不能为空");
        this.HOST = host;
        client = new ClientConfig(key).bulid();
    }


    private final String SEND_SIMPLE_MAIL_V1 = "/v1/mail/sendSimpleMail";
    /**
     * 发送简单邮件
     * @param sendMailParam
     * @return
     */
    public Result<SendMailResult> sendSimpleMailV1(SendMailParam sendMailParam) throws IOException {
        Request request = new Request.Builder()
                .post(createJsonBody(sendMailParam))
                .url(HOST+SEND_SIMPLE_MAIL_V1)
                .build();
        try(Response response = client.newCall(request).execute();){
            return gson.fromJson(response.body().string(),new TypeToken<Result<SendMailResult>>() {}.getType());
        }
    }

    private final String GET_MAIL_BY_ID_V1 = "/v1/mail/";
    /**
     * 根据id获取邮件信息
     * @param id
     * @return
     */
    public Result<GetMailByIdResult> getMailByIdV1(Long id) throws IOException {

        Request request = new Request.Builder()
                .get()
                .url(HOST+GET_MAIL_BY_ID_V1+id)
                .build();
        try(Response response = client.newCall(request).execute();){
            return gson.fromJson(response.body().string(),new TypeToken<Result<GetMailByIdResult>>() {}.getType());
        }
    }

    public static final MediaType JSON = MediaType.get("application/json");
    private RequestBody createJsonBody(Object json){
        return RequestBody.create(JSON, gson.toJson(json));
    }
}
