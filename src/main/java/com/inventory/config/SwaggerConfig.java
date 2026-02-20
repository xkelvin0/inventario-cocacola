package com.inventory.config;

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
                .info(new Info()
                        .title("Sistema de Gestión de Inventario - API REST")
                        .version("1.0.0")
                        .description(
                                "API REST para la gestión de inventario. " +
                                "Permite administrar Productos, Lotes, Movimientos (Ingreso/Salida) " +
                                "e Inventario con reportes. " +
                                "Desarrollado con Spring Boot, JPA/Hibernate y MySQL."
                        )
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("soporte@inventory.com")
                        )
                );
    }
}
