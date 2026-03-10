package dr.sbs.front.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "Spring Boot Starter Front Application",
            version = "1.0",
            description = "Spring Boot Starter Front Application"))
public class SpringDocConfig {}
