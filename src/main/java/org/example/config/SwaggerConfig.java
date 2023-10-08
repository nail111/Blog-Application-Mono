package org.example.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Blog app Rest API",
                description = "Spring Boot Blog app REST API documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Nail",
                        email = "nailtagiyev1999@gmail.com",
                        url = "http://nail123.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://nail123.com/licence"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Blog app documentation",
                url = "http://nail123.com/some-link"
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
}