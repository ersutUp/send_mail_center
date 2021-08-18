package top.ersut.mail.send.web.model.entity.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
/**
 * @author ersut
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "apps")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 秘钥
     */
    private String key;

    /**
     * 接收邮箱
     */
    private String recipientMail;

    /**
     * 是否冻结 1:正常 2:冻结
     */
    private Integer freezeFlag;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
