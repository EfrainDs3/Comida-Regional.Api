package web.Regional_Api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RegionalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegionalApiApplication.class, args);
	}

}
