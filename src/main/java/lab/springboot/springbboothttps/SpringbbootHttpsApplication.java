package lab.springboot.springbboothttps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class SpringbbootHttpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbbootHttpsApplication.class, args);
    }

}
