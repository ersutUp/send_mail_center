package top.ersut.mail.send.model.v1.mail.result;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetMailByIdResult {

    private Long id;

    /**
     * 拼接签名后的标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 发送状态 1:发送成功 2:发送失败
     */
    private Integer sendStatus;


    private LocalDateTime createAt;
}
