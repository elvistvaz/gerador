package br.com.icep.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do OpenAPI/Swagger.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("ICEP API")
                .version("1.0.0")
                .description("Sistema de Gestão Educacional ICEP")
                .contact(new Contact()
                    .name("Suporte")
                    .email("suporte@empresa.com")));
    }
}
