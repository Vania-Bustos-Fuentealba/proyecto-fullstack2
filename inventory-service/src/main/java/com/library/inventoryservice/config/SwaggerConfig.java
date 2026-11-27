// Nota de lectura: Ubica la clase dentro del paquete com.library.inventoryservice.config.
package com.library.inventoryservice.config;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.models.OpenAPI;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.models.info.Info;
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
                // Nota de lectura: Encadena la operación info al resultado anterior.
                .info(new Info()
                        // Nota de lectura: Encadena la operación title al resultado anterior.
                        .title("Inventory Service API")
                        // Nota de lectura: Encadena la operación version al resultado anterior.
                        .version("1.0")
                        // Nota de lectura: Encadena la operación description al resultado anterior.
                        .description("Microservicio de inventario - Biblioteca Online"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
