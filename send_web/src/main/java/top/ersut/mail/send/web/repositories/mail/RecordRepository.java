package top.ersut.mail.send.web.repositories.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.ersut.mail.send.web.model.entity.mail.Record;

/**
 * @author Administrator
 */
@Repository
public interface RecordRepository extends JpaRepository<Record,Long> {

    @Modifying
    @Query("update mail_records set send_status = :sendStatus where id = :id")
    int updateSendStatusById(int sendStatus, Long id);

}
