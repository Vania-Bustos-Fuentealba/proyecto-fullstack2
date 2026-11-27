// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.security.
package com.library.authservice.security;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.Bean;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.Configuration;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.crypto.password.PasswordEncoder;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.web.SecurityFilterChain;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Configuration
// Nota de lectura: Declara el tipo SecurityConfig y su contrato.
public class SecurityConfig {

    // Nota de lectura: Publica el objeto como dependencia gestionada por Spring.
    @Bean
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {

        // Nota de lectura: Aporta el valor http a la declaración o llamada abierta.
        http
                // Nota de lectura: Encadena la operación csrf al resultado anterior.
                .csrf(csrf -> csrf.disable())
                // Nota de lectura: Encadena la operación sessionManagement al resultado anterior.
                .sessionManagement(session -> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                // Nota de lectura: Encadena la operación addFilterBefore al resultado anterior.
                .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                // Nota de lectura: Encadena la operación exceptionHandling al resultado anterior.
                .exceptionHandling(errors -> errors
                        // Nota de lectura: Encadena la operación authenticationEntryPoint al resultado anterior.
                        .authenticationEntryPoint((req, res, ex) -> res.sendError(401))
                        // Nota de lectura: Encadena la operación accessDeniedHandler al resultado anterior.
                        .accessDeniedHandler((req, res, ex) -> res.sendError(403)))
                // Nota de lectura: Encadena la operación authorizeHttpRequests al resultado anterior.
                .authorizeHttpRequests(auth -> auth
                        // Nota de lectura: Permite acceder a la ruta pública especificada sin autenticar.
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/auth/login", "/auth/register").permitAll()
                        // Nota de lectura: Restringe la ruta al rol indicado en el servidor.
                        .requestMatchers("/auth/users", "/auth/users/**").hasRole("ADMIN")
                        // Nota de lectura: Exige una identidad autenticada antes de permitir la ruta.
                        .requestMatchers("/auth/session").authenticated()
                        // Nota de lectura: Exige una identidad autenticada antes de permitir la ruta.
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/auth/refresh").authenticated()
                        // Nota de lectura: Permite acceder a la ruta pública especificada sin autenticar.
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Nota de lectura: Deniega rutas que no hayan sido permitidas antes.
                        .anyRequest().denyAll()
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                );

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return http.build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Publica el objeto como dependencia gestionada por Spring.
    @Bean
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public PasswordEncoder passwordEncoder() {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return new BCryptPasswordEncoder();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
