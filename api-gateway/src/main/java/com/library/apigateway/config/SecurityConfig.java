// Nota de lectura: Ubica la clase dentro del paquete com.library.apigateway.config.
package com.library.apigateway.config;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.Bean;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.Configuration;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.beans.factory.annotation.Value;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.config.Customizer;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.web.SecurityFilterChain;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.cors.CorsConfiguration;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.cors.CorsConfigurationSource;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Configuration
// Nota de lectura: Declara el tipo SecurityConfig y su contrato.
public class SecurityConfig {

    // El puerto del frontend puede cambiar en una demostración. Se admiten solo los orígenes configurados.
    // Nota de lectura: Lee una propiedad de configuración o variable de entorno; no escribe un secreto en el código.
    @Value("${web.allowed-origins:http://localhost:5173,http://127.0.0.1:5173}")
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    private List<String> allowedOrigins;

    // Nota de lectura: Publica el objeto como dependencia gestionada por Spring.
    @Bean
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Nota de lectura: Aporta el valor http a la declaración o llamada abierta.
        http
                // Nota de lectura: Encadena la operación cors al resultado anterior.
                .cors(Customizer.withDefaults())
                // Nota de lectura: Encadena la operación csrf al resultado anterior.
                .csrf(csrf -> csrf.disable())
                // Nota de lectura: Encadena la operación authorizeHttpRequests al resultado anterior.
                .authorizeHttpRequests(auth -> auth
                        // Nota de lectura: Permite acceder a la ruta pública especificada sin autenticar.
                        .anyRequest().permitAll()
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                )
                // Nota de lectura: Encadena la operación httpBasic al resultado anterior.
                .httpBasic(Customizer.withDefaults());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return http.build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Publica el objeto como dependencia gestionada por Spring.
    @Bean
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public CorsConfigurationSource corsConfigurationSource() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CorsConfiguration config = new CorsConfiguration();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        config.setAllowedOrigins(allowedOrigins);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        config.setAllowedHeaders(List.of("*"));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        config.setAllowCredentials(false);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        source.registerCorsConfiguration("/**", config);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return source;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
