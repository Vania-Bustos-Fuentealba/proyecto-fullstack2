// Nota de lectura: Ubica la clase dentro del paquete com.library.apigateway.handler.
package com.library.apigateway.handler;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.ResponseEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.ExceptionHandler;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Nota de lectura: Registra un controlador que responde a HTTP con datos serializados.
@RestControllerAdvice
// Nota de lectura: Declara el tipo GlobalExceptionHandler y su contrato.
public class GlobalExceptionHandler {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(Exception.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<String> handleGeneralException(Exception ex) {

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity
                // Nota de lectura: Encadena la operación status al resultado anterior.
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                // Nota de lectura: Encadena la operación body al resultado anterior.
                .body("Error interno del servidor.");
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(RuntimeException.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity
                // Nota de lectura: Encadena la operación status al resultado anterior.
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                // Nota de lectura: Encadena la operación body al resultado anterior.
                .body("No se pudo completar la operación.");
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    // Un servicio caído es un fallo de dependencia, no un error de datos del usuario.
    @ExceptionHandler(org.springframework.web.client.ResourceAccessException.class)
    public ResponseEntity<String> handleUnavailable(org.springframework.web.client.ResourceAccessException ex) {
        // No devuelve nombres internos de contenedores, rutas ni detalles de conexión.
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("El servicio no está disponible. Intenta nuevamente.");
    }
    // Conserva el 404 de una ruta que no existe, sin revelar detalles internos.
    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    public ResponseEntity<String> handleMissing(org.springframework.web.servlet.resource.NoResourceFoundException ex) {
        // El navegador puede distinguir un recurso inexistente de un servidor caído.
        return ResponseEntity.notFound().build();
    }
}
