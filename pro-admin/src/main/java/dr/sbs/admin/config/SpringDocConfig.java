package dr.sbs.admin.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "Spring Boot Starter Admin Application",
            version = "1.0",
            description = "Spring Boot Starter Admin Application"))
public class SpringDocConfig {}
