// Nota de lectura: Ubica la clase dentro del paquete com.library.paymentservice.controller.
package com.library.paymentservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.dto.PaymentRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.dto.PaymentResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.service.PaymentService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.Operation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.media.Content;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.media.ExampleObject;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.media.Schema;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.responses.ApiResponse;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.responses.ApiResponses;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.tags.Tag;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.Valid;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.ResponseEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.*;

// Nota de lectura: Registra un controlador que responde a HTTP con datos serializados.
@RestController
// Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
@RequestMapping("/payments")
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Documenta la operación para Swagger/OpenAPI.
@Tag(name = "Payments", description = "Operaciones de gestión de pagos")
// Nota de lectura: Declara el tipo PaymentController y su contrato.
public class PaymentController {

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final PaymentService paymentService;

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PostMapping
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Crear pago", description = "Registra un nuevo pago para una orden existente")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            schema = @Schema(implementation = PaymentResponseDTO.class),
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "{\"id\": 1, \"orderId\": 1, \"amount\": 29.99, \"paymentMethod\": \"CREDIT_CARD\", \"status\": \"COMPLETED\"}"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "400", description = "Datos inválidos o pago ya existe para esa orden"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<PaymentResponseDTO> createPayment(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    description = "Datos del pago a registrar", required = true,
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(examples = @ExampleObject(
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            value = "{\"orderId\": 1, \"amount\": 29.99, \"paymentMethod\": \"CREDIT_CARD\", \"status\": \"COMPLETED\"}")))
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Valid @RequestBody PaymentRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Creando pago para orderId {}", request.getOrderId());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(request));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}