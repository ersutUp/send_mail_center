package top.ersut.mail.send.web.repositories.sign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.ersut.mail.send.web.model.entity.sign.Sign;

/**
 * @author ersut
 */
@Repository
public interface SignRepository extends JpaRepository<Sign,Long> {

}
