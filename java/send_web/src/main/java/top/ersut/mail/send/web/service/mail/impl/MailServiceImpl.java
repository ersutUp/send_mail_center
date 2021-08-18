package top.ersut.mail.send.web.service.mail.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.ersut.mail.send.model.common.result.Result;
import top.ersut.mail.send.model.common.result.code.SystemResultCode;
import top.ersut.mail.send.model.v1.mail.result.SendMailResult;
import top.ersut.mail.send.web.model.entity.mail.Record;
import top.ersut.mail.send.web.repositories.mail.RecordRepository;
import top.ersut.mail.send.web.service.mail.IMailService;
import top.ersut.mail.send.web.service.mail.SendMail;

@Slf4j
@Service
public class MailServiceImpl implements IMailService {


    @Autowired
    private SendMail sendMail;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    @Lazy
    private MailServiceImpl mailServiceImpl;

    /**
     * 发送简单邮件
     * 由于发送太慢改为异步
     * @param record
     * @return
     */
    @Override
    @Transactional
    public Result sendSimpleMail(Record record) {
        record = recordRepository.save(record);
        log.info("邮件发送记录[{}]",record.toString());
        mailServiceImpl.sendSimpleMailAsync(record.getId(),record.getTitle(),record.getContent(),record.getRecipientMail());
        return Result.SUCCESS.setData(SendMailResult.builder().mailRecordId(record.getId()).build());
    }


    @Async
    @Transactional
    public void sendSimpleMailAsync(Long recordId,String title,String content,String recipientMail){
        boolean isOk = sendMail.sendSimpleMail(title,content,recipientMail);
        log.info("send mail[{}] to [{}] [{}]",recordId,recipientMail,isOk);
        if(isOk){
            recordRepository.updateSendStatusById(1,recordId);
        } else {
            recordRepository.updateSendStatusById(2,recordId);
        }
    }

}
