package top.ersut.mail.send.web.service.mail;

import top.ersut.mail.send.model.common.result.Result;
import top.ersut.mail.send.web.model.entity.mail.Record;

public interface IMailService {

    /**
     * 发送简单邮件
     * @param record
     * @return
     */
    Result sendSimpleMail(Record record);

}
