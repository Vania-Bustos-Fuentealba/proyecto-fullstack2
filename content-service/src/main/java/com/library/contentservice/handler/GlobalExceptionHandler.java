// Nota de lectura: Ubica la clase dentro del paquete com.library.contentservice.handler.
package com.library.contentservice.handler;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.exceptions.ResourceNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.ResponseEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.converter.HttpMessageNotReadableException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.MethodArgumentNotValidException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.ExceptionHandler;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.RestControllerAdvice;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
    @ExceptionHandler(ResourceNotFoundException.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.warn("Error de validación en la petición");
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Map<String, String> errors = new HashMap<>();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        ex.getBindingResult().getFieldErrors()
                // Nota de lectura: Encadena la operación forEach al resultado anterior.
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return build(HttpStatus.BAD_REQUEST, "Error de validación", errors);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException ex) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.warn("Cuerpo de la petición ilegible: {}", ex.getMessage());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return build(HttpStatus.BAD_REQUEST, "El cuerpo de la petición no es un JSON válido", null);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(NoResourceFoundException.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Map<String, Object>> handleNoResource(NoResourceFoundException ex) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return build(HttpStatus.NOT_FOUND, "Recurso no encontrado", null);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(Exception.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.error("Error interno del servidor", ex);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", null);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message, Object errors) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Map<String, Object> response = new HashMap<>();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("status", status.value());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.put("message", message);
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (errors != null) {
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            response.put("errors", errors);
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(status).body(response);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}