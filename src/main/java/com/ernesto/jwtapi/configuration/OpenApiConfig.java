package com.ernesto.jwtapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI openAPIWithJWT() {
                final String securitySchemeName = "bearerAuth";
                return new OpenAPI()
                                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                                .components(new Components().addSecuritySchemes(securitySchemeName,
                                                new SecurityScheme()
                                                                .name(securitySchemeName)
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")))
                                .info(new Info()
                                                .title("API JWT de Ernesto")
                                                .version("1.0")
                                                .description("API para autenticación y gestión de usuarios con JWT"));
        }
}