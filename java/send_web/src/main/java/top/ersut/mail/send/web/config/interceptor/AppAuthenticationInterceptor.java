package top.ersut.mail.send.web.config.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import top.ersut.mail.send.model.common.result.Result;
import top.ersut.mail.send.model.common.result.code.SystemResultCode;
import top.ersut.mail.send.web.config.JacksonConfig;
import top.ersut.mail.send.web.model.entity.app.App;
import top.ersut.mail.send.web.repositories.app.AppRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Component
public class AppAuthenticationInterceptor implements HandlerInterceptor {

    private static final String KEY = "key";

    public static final String APP_SESSION = "APP_SESSION";

    private static final String CONTENTTYPE = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8";

    @Autowired
    private AppRepository appRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String key = request.getHeader(KEY);

        if(ObjectUtils.isEmpty(key)){
            response.setContentType(CONTENTTYPE);
            response.getWriter().write(objectMapper.writeValueAsString(new Result(SystemResultCode.PARAM_ERROR,"头信息缺少")));
            return false;
        }

        //查询应用
        Optional<App> optionalApp = appRepository.findOne(Example.of(App.builder().key(key).build()));
        if(optionalApp.isPresent()){
            log.info("应用 [{}] 发起 [{}] 请求 [{}]",optionalApp.get().getName(),request.getMethod(),request.getRequestURI());
            request.getSession().setAttribute(APP_SESSION,objectMapper.writeValueAsString(optionalApp.get()));
            return true;
        } else {
            log.info("未知 key:[{}] 请求",key);
            response.setContentType(CONTENTTYPE);
            response.getWriter().write(objectMapper.writeValueAsString(new Result(SystemResultCode.APP_NOT_EXIST)));
            return false;
        }
    }

    public static App sessionGetApp(HttpSession session){
        try {
            return JacksonConfig.OBJECTMAPPER.readValue(session.getAttribute(AppAuthenticationInterceptor.APP_SESSION).toString(), App.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
