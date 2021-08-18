package top.ersut.mail.send.web.repositories.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.ersut.mail.send.web.model.entity.app.AppSign;

import java.util.Optional;

/**
 * @author ersut
 */
@Repository
public interface AppSignRepository extends JpaRepository<AppSign,Long> {

    Optional<AppSign> findByAppIdAndSignId(Long appId, Long signId);

}
