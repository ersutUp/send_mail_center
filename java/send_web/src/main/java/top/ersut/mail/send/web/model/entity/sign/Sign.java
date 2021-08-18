package top.ersut.mail.send.web.model.entity.sign;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author ersut
 */
@Data
@Entity(name = "signs")
public class Sign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 签名名称
     */
    private String name;

    private LocalDateTime createAt;
}
