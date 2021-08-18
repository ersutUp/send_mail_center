package top.ersut.mail.send.web.repositories.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.ersut.mail.send.web.model.entity.app.App;

/**
 * @author ersut
 */
@Repository
public interface AppRepository extends JpaRepository<App,Long> {

}
