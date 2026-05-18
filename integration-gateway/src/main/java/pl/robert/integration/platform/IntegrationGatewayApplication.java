package pl.robert.integration.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IntegrationGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(IntegrationGatewayApplication.class, args);
  }

}
