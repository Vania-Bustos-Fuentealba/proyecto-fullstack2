// Nota de lectura: Ubica la clase dentro del paquete com.library.orderservice.handler.
package com.library.orderservice.handler;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.ResponseEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.MethodArgumentNotValidException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.ExceptionHandler;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.HashMap;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Map;

// Nota de lectura: Registra un controlador que responde a HTTP con datos serializados.
@RestControllerAdvice
// Nota de lectura: Declara el tipo GlobalExceptionHandler y su contrato.
public class GlobalExceptionHandler {

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.warn("Error de validación en la petición");

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Map<String, String> errors = new HashMap<>();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        ex.getBindingResult().getFieldErrors()
                // Nota de lectura: Encadena la operación forEach al resultado anterior.
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Map<String, Object> response = new HashMap<>();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("status", HttpStatus.BAD_REQUEST.value());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("message", "Error de validación");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("errors", errors);

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(RuntimeException.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.warn("Error de negocio: {}", ex.getMessage());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Map<String, Object> response = new HashMap<>();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("status", HttpStatus.BAD_REQUEST.value());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("message", "Error en la solicitud");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("details", ex.getMessage());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(Exception.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.error("Error interno del servidor", ex);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Map<String, Object> response = new HashMap<>();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("message", "Error interno del servidor");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("details", ex.getMessage());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Map<String, Object>> handleNoResourceFound(
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            org.springframework.web.servlet.resource.NoResourceFoundException ex) {

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Map<String, Object> response = new HashMap<>();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("status", HttpStatus.NOT_FOUND.value());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("message", "Recurso no encontrado");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("details", ex.getMessage());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}