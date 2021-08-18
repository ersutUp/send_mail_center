package top.ersut.mail.send.web.model.entity.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author ersut
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "mail_records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * 接收邮箱
     */
    private String recipientMail;

    /**
     * 发送状态 1:发送成功 2:发送失败
     */
    private Integer sendStatus;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 签名id
     */
    private Long signId;

    private LocalDateTime createAt;
}
