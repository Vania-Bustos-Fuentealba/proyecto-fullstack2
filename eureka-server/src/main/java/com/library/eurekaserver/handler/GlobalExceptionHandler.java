// Nota de lectura: Ubica la clase dentro del paquete com.library.eurekaserver.handler.
package com.library.eurekaserver.handler;

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
                .body("Error interno del servidor: " + ex.getMessage());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ExceptionHandler(RuntimeException.class)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity
                // Nota de lectura: Encadena la operación status al resultado anterior.
                .status(HttpStatus.BAD_REQUEST)
                // Nota de lectura: Encadena la operación body al resultado anterior.
                .body("Error: " + ex.getMessage());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}