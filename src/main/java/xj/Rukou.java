package xj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Rukou {
    public static void main(String[] args) {
        SpringApplication.run(Rukou.class, args);
    }
}
