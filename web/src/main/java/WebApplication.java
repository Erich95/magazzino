import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rgi.*"})
@EntityScan(basePackages = {"com.rgi.*"})
@EnableJpaRepositories(basePackages = {"com.rgi.*"})
public class WebApplication {
     public static void main(String[] args) {
         SpringApplication.run(WebApplication.class);

     }
}
