// Nota de lectura: Ubica la clase dentro del paquete com.library.contentservice.config.
package com.library.contentservice.config;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.models.Components;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.models.OpenAPI;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.models.info.Info;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.models.security.SecurityScheme;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.Bean;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.Configuration;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Configuration
// Nota de lectura: Declara el tipo SwaggerConfig y su contrato.
public class SwaggerConfig {

    // Nota de lectura: Publica el objeto como dependencia gestionada por Spring.
    @Bean
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public OpenAPI customOpenAPI() {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return new OpenAPI()
                // Las pruebas de Swagger deben pasar por el mismo Gateway que React.
                // Nota de lectura: Encadena la operación servers al resultado anterior.
                .servers(java.util.List.of(new io.swagger.v3.oas.models.servers.Server().url("/")))
                // Nota de lectura: Encadena la operación info al resultado anterior.
                .info(new Info()
                        // Nota de lectura: Encadena la operación title al resultado anterior.
                        .title("Content Service API")
                        // Nota de lectura: Encadena la operación version al resultado anterior.
                        .version("1.0")
                        // Nota de lectura: Encadena la operación description al resultado anterior.
                        .description("Microservicio de contenido (Blog y Contacto) - Biblioteca Online"))
                // Nota de lectura: Encadena la operación components al resultado anterior.
                .components(new Components()
                        // Nota de lectura: Encadena la operación addSecuritySchemes al resultado anterior.
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                // Nota de lectura: Encadena la operación type al resultado anterior.
                                .type(SecurityScheme.Type.HTTP)
                                // Nota de lectura: Encadena la operación scheme al resultado anterior.
                                .scheme("bearer")
                                // Nota de lectura: Encadena la operación bearerFormat al resultado anterior.
                                .bearerFormat("JWT")));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
