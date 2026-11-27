// Nota de lectura: Ubica la clase dentro del paquete com.library.inventoryservice.config.
package com.library.inventoryservice.config;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.Bean;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.Configuration;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Configuration
// Nota de lectura: Declara el tipo RestTemplateConfig y su contrato.
public class RestTemplateConfig {

    // Nota de lectura: Publica el objeto como dependencia gestionada por Spring.
    @Bean
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public RestTemplate restTemplate() {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return new RestTemplate();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}