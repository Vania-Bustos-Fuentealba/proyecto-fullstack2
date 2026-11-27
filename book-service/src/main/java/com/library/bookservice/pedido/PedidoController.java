// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.pedido.
package com.library.bookservice.pedido;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.ResponseEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.GetMapping;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.PathVariable;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.PostMapping;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.RequestBody;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.RequestMapping;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.RestController;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.Operation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.tags.Tag;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.Valid;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.RequestHeader;

// Nota de lectura: Registra un controlador que responde a HTTP con datos serializados.
@RestController
// Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
@RequestMapping("/pedidos")
// Nota de lectura: Documenta la operación para Swagger/OpenAPI.
@Tag(name = "Pedidos", description = "Checkout: creación y consulta de pedidos")
// Nota de lectura: Declara el tipo PedidoController y su contrato.
public class PedidoController {

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final PedidoService servicio;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public PedidoController(PedidoService servicio) {
        // Nota de lectura: Asigna al objeto la dependencia o el valor recibido por el método.
        this.servicio = servicio;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PostMapping
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Crea un pedido: valida stock, aplica cupón, calcula envío y descuenta stock")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<PedidoResponse> crear(@Valid @RequestBody PedidoRequest pedido,
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @io.swagger.v3.oas.annotations.Parameter(hidden = true) @RequestHeader("X-User-Id") String propietario,
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @RequestHeader("Idempotency-Key") String clave) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(pedido, propietario, clave));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/{id}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Obtiene un pedido por su id")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public PedidoResponse obtener(@PathVariable Long id, @io.swagger.v3.oas.annotations.Parameter(hidden = true) @RequestHeader("X-User-Id") String propietario, @io.swagger.v3.oas.annotations.Parameter(hidden = true) @RequestHeader("X-User-Role") String rol) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return servicio.obtener(id, propietario, rol);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Lista los pedidos para ADMIN o SELLER, del más reciente al más antiguo")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<PedidoResponse> listar() {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return servicio.listar();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
