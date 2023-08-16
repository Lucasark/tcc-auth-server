package tcc.uff.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class SpringAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthorizationServerApplication.class, args);
    }

}
