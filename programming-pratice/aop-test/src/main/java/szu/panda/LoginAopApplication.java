package szu.panda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class LoginAopApplication {

	public static void main(String[] args) {

		SpringApplication.run(LoginAopApplication.class, args);
	}
}
