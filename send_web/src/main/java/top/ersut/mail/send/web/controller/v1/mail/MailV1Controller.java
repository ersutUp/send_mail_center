package top.ersut.mail.send.web.controller.v1.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.ersut.mail.send.model.common.result.Result;
import top.ersut.mail.send.model.common.result.code.SystemResultCode;
import top.ersut.mail.send.model.v1.mail.result.GetMailByIdResult;
import top.ersut.mail.send.web.config.interceptor.AppAuthenticationInterceptor;
import top.ersut.mail.send.web.model.entity.app.App;
import top.ersut.mail.send.web.model.entity.app.AppSign;
import top.ersut.mail.send.web.model.entity.mail.Record;
import top.ersut.mail.send.web.model.entity.sign.Sign;
import top.ersut.mail.send.model.v1.mail.param.SendMailParam;
import top.ersut.mail.send.web.repositories.app.AppRepository;
import top.ersut.mail.send.web.repositories.app.AppSignRepository;
import top.ersut.mail.send.web.repositories.mail.RecordRepository;
import top.ersut.mail.send.web.repositories.sign.SignRepository;
import top.ersut.mail.send.web.service.mail.IMailService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/mail")
public class MailV1Controller {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private SignRepository signRepository;

    @Autowired
    private AppSignRepository appSignRepository;

    @Autowired
    private IMailService mailService;

    @PostMapping("/sendSimpleMail")
    public Result sendSimpleMail(HttpServletRequest request, @RequestBody @Validated SendMailParam sendMailParam){

        //获取session中的app
        App app = AppAuthenticationInterceptor.sessionGetApp(request.getSession());

        //查询签名
        Optional<Sign> optionalSign = signRepository.findById(sendMailParam.getSignId());
        if(!optionalSign.isPresent()){
            return new Result(SystemResultCode.SIGN_NOT_EXIST);
        }

        //校验是否授权签名给应用
        Optional<AppSign> optionalAppSign = appSignRepository.findByAppIdAndSignId(app.getId(), optionalSign.get().getId());
        if(!optionalAppSign.isPresent()){
            return new Result(SystemResultCode.SIGN_UNAUTHORIZED);
        }

        //组装实体
        Record record = Record.builder()
                .appId(app.getId())
                .signId(optionalSign.get().getId())
                .content(sendMailParam.getContent())
                .title("【"+optionalSign.get().getName()+"】"+sendMailParam.getTitle())
                .sendStatus(-1)
                .recipientMail(app.getRecipientMail())
                .build();

        //进入service
        return mailService.sendSimpleMail(record);
    }


    @GetMapping("/{id}")
    public Result<GetMailByIdResult> getMailById(@PathVariable Long id){

        Record record = recordRepository.getById(id);

        if(record == null){
            return new Result(SystemResultCode.DATA_NOT_FIND);
        }

        GetMailByIdResult getMailByIdResult = new GetMailByIdResult();
        BeanUtils.copyProperties(record,getMailByIdResult);

        return Result.SUCCESS.setData(getMailByIdResult);
    }

}
