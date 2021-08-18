package top.ersut.mail.send;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//开启AOP
@EnableAspectJAutoProxy
//开启jpa
@EnableJpaRepositories
//开启异步线程
@EnableAsync
public class SendMailCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendMailCenterApplication.class, args);
	}

}
