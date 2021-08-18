package top.ersut.mail.send.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ersut.mail.send.model.common.result.Result;

@Slf4j
@SpringBootTest
class SendMailCenterApplicationTests {

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void contextLoads() throws JsonProcessingException {
		String s = objectMapper.writeValueAsString(Result.SUCCESS);
		log.info(s);
	}

}
