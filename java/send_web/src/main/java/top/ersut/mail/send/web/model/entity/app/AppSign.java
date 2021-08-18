package top.ersut.mail.send.web.model.entity.app;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ersut
 */
@Data
@Entity(name = "app_sign")
public class AppSign implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long appId;

    private Long signId;

    private LocalDateTime createAt;

}
