package by.spaces.calculator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Online Calculator Api")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .url("https://spacescumers.com")
                                                .email("spacescumers_support@gmail.com")
                                                .name("Space Scumers")
                                )
                );
    }
}
