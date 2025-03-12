package unihelp.example.groupe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GroupeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupeApplication.class, args);
	}

}
