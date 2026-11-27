// Nota de lectura: Ubica la clase dentro del paquete com.library.apigateway.config.
package com.library.apigateway.config;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.apigateway.filter.JwtWebMvcFilter;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.beans.factory.annotation.Autowired;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.cloud.gateway.server.mvc.filter.FilterSupplier;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.Bean;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.Configuration;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.function.HandlerFilterFunction;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.function.ServerResponse;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.lang.reflect.Method;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Collection;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Configuration
// Nota de lectura: Declara el tipo GatewayConfig y su contrato.
public class GatewayConfig {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static JwtWebMvcFilter jwtWebMvcFilterInstance;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Autowired
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setJwtWebMvcFilterInstance(JwtWebMvcFilter filter) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        GatewayConfig.jwtWebMvcFilterInstance = filter;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Publica el objeto como dependencia gestionada por Spring.
    @Bean
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public FilterSupplier jwtFilterSupplier() {

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return new FilterSupplier() {

            // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
            @Override
            // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
            public Collection<Method> get() {

                // Nota de lectura: Agrupa operaciones que pueden fallar para poder gestionar sus errores.
                try {
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return List.of(GatewayConfig.class.getMethod("jwtFilter"));
                // Nota de lectura: Captura el error y elige una respuesta controlada en lugar de mostrar éxito.
                } catch (NoSuchMethodException e) {
                    // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
                    throw new RuntimeException(e);
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                }
            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
            }
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        };
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public static HandlerFilterFunction<ServerResponse, ServerResponse> jwtFilter() {

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return jwtWebMvcFilterInstance;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
