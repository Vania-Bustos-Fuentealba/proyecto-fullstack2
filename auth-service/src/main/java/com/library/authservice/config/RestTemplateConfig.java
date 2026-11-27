// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.config.
package com.library.authservice.config;

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
        // Un perfil caído no debe mantener una transacción abierta indefinidamente.
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var factory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        factory.setConnectTimeout(3000);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        factory.setReadTimeout(5000);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return new RestTemplate(factory);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
