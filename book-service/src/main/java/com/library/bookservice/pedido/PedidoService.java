// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.pedido.
package com.library.bookservice.pedido;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.time.LocalDateTime;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.HashSet;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Map;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Set;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.transaction.annotation.Transactional;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.server.ResponseStatusException;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.entity.Book;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.repository.BookRepository;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Declara el tipo PedidoService y su contrato.
public class PedidoService {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    public static final int COSTO_ENVIO = 3990;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    public static final int MONTO_ENVIO_GRATIS = 30000;

    // Cupones disponibles y su porcentaje de descuento (mismos que usa el frontend)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static final Map<String, Double> CUPONES = Map.of(
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            "DUOC10", 0.10,
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            "LIBROS5", 0.05);

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final PedidoRepository pedidoRepository;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final BookRepository libroRepository;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public PedidoService(PedidoRepository pedidoRepository, BookRepository libroRepository) {
        // Nota de lectura: Asigna al objeto la dependencia o el valor recibido por el método.
        this.pedidoRepository = pedidoRepository;
        // Nota de lectura: Asigna al objeto la dependencia o el valor recibido por el método.
        this.libroRepository = libroRepository;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // @Transactional: si algo falla, se deshacen todos los cambios (incluido el stock)
    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public PedidoResponse crear(PedidoRequest datos, String propietario, String clave) {
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (propietario == null || propietario.isBlank()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (clave == null || !clave.matches("[a-zA-Z0-9-]{16,64}"))
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta una clave válida para confirmar el pedido");
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String solicitud = propietario + ":" + clave;
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String huella = huella(datos);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        var anterior = pedidoRepository.findBySolicitud(solicitud);
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (anterior.isPresent()) {
            // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
            if (!anterior.get().getHuella().equals(huella))
                // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El intento anterior contiene otros datos");
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return aRespuesta(anterior.get());
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Pedido pedido = new Pedido();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        pedido.setFecha(LocalDateTime.now());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        pedido.setPropietario(propietario);
        // Nota de lectura: Guarda la clave de este intento para reconocer reenvíos del mismo pedido.
        pedido.setSolicitud(solicitud);
        // Nota de lectura: Guarda el resumen del contenido para rechazar una clave reutilizada con otros datos.
        pedido.setHuella(huella);
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        pedido.setCliente(datos.cliente().trim());
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        pedido.setCorreo(datos.correo().trim().toLowerCase());
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        pedido.setDireccion(datos.direccion().trim());
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        pedido.setComuna(datos.comuna().trim());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        pedido.setMetodoPago(datos.metodoPago());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Set<Long> yaAgregados = new HashSet<>();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        java.math.BigDecimal subtotal = java.math.BigDecimal.ZERO;

        // El mismo orden de bloqueo evita que dos compras descuenten la última unidad.
        // Nota de lectura: Recorre los elementos indicados y ejecuta el bloque para cada uno.
        for (PedidoRequest.ItemSolicitado solicitado : datos.items().stream()
                // Nota de lectura: Encadena la operación sorted al resultado anterior.
                .sorted(java.util.Comparator.comparing(PedidoRequest.ItemSolicitado::libroId)).toList()) {
            // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
            if (!yaAgregados.add(solicitado.libroId())) {
                // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                        "El libro con id " + solicitado.libroId() + " está repetido en el pedido");
            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
            }

            // El precio sale SIEMPRE de la base de datos, nunca de lo que envía el cliente
            // Nota de lectura: Busca y bloquea el libro hasta terminar la transacción para proteger el stock.
            Book libro = libroRepository.findForUpdate(solicitado.libroId())
                    // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                            "No existe un libro con id " + solicitado.libroId()));

            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            int cantidad = solicitado.cantidad();

            // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
            if (libro.getStock() < cantidad) {
                // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                        "Stock insuficiente para \"" + libro.getTitle() + "\": quedan "
                                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                                + libro.getStock() + " unidad(es)");
            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
            }

            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            libro.setStock(libro.getStock() - cantidad);

            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            PedidoItem item = new PedidoItem();
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            item.setLibro(libro);
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            item.setNombreLibro(libro.getTitle());
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            item.setPrecioUnitario(java.math.BigDecimal.valueOf(libro.getPrice()).setScale(2, java.math.RoundingMode.HALF_UP));
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            item.setCantidad(cantidad);
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            item.setSubtotal(item.getPrecioUnitario().multiply(java.math.BigDecimal.valueOf(cantidad)));
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            pedido.agregarItem(item);

            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            subtotal = subtotal.add(item.getSubtotal());
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String cupon = normalizarCupon(datos.cupon());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        java.math.BigDecimal descuento = calcularDescuento(subtotal, cupon);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        java.math.BigDecimal envio = calcularEnvio(subtotal.subtract(descuento));

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        pedido.setCupon(cupon);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        pedido.setSubtotal(subtotal);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        pedido.setDescuento(descuento);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        pedido.setEnvio(envio);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        pedido.setTotal(subtotal.subtract(descuento).add(envio));

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return aRespuesta(pedidoRepository.save(pedido));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional(readOnly = true)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public PedidoResponse obtener(Long id, String propietario, String rol) {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Pedido pedido = pedidoRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                        "No existe un pedido con id " + id));
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (!"ADMIN".equals(rol) && !pedido.getPropietario().equals(propietario))
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Este pedido pertenece a otra cuenta");
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return aRespuesta(pedido);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional(readOnly = true)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<PedidoResponse> listar() {
        // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
        return pedidoRepository.findAllByOrderByFechaDesc().stream()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .map(this::aRespuesta)
                // Nota de lectura: Encadena la operación toList al resultado anterior.
                .toList();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // ---------- Reglas de negocio ----------

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private String normalizarCupon(String texto) {
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (texto == null || texto.isBlank()) {
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return null;
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        String codigo = texto.trim().toUpperCase();
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (!CUPONES.containsKey(codigo)) {
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                    "El cupón " + codigo + " no es válido");
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return codigo;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private java.math.BigDecimal calcularDescuento(java.math.BigDecimal subtotal, String cupon) {
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (cupon == null) return java.math.BigDecimal.ZERO;
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return subtotal.multiply(java.math.BigDecimal.valueOf(CUPONES.get(cupon)))
                // Nota de lectura: Encadena la operación setScale al resultado anterior.
                .setScale(0, java.math.RoundingMode.HALF_UP);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private java.math.BigDecimal calcularEnvio(java.math.BigDecimal base) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return base.signum() == 0 || base.compareTo(java.math.BigDecimal.valueOf(MONTO_ENVIO_GRATIS)) >= 0
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                ? java.math.BigDecimal.ZERO : java.math.BigDecimal.valueOf(COSTO_ENVIO);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private String huella(PedidoRequest datos) {
        // Nota de lectura: Agrupa operaciones que pueden fallar para poder gestionar sus errores.
        try {
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            byte[] hash = java.security.MessageDigest.getInstance("SHA-256")
                    // Nota de lectura: Encadena la operación digest al resultado anterior.
                    .digest(datos.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return java.util.HexFormat.of().formatHex(hash);
        // Nota de lectura: Captura el error y elige una respuesta controlada en lugar de mostrar éxito.
        } catch (java.security.NoSuchAlgorithmException ex) {
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new IllegalStateException(ex);
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private PedidoResponse aRespuesta(Pedido pedido) {
        // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
        List<PedidoResponse.ItemVendido> items = pedido.getItems().stream()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .map(item -> new PedidoResponse.ItemVendido(
                        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                        item.getLibro().getId(),
                        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                        item.getNombreLibro(),
                        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                        item.getPrecioUnitario(),
                        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                        item.getCantidad(),
                        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                        item.getSubtotal()))
                // Nota de lectura: Encadena la operación toList al resultado anterior.
                .toList();

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return new PedidoResponse(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getId(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getFecha(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getCliente(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getCorreo(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getDireccion(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getComuna(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getMetodoPago(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getCupon(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getSubtotal(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getDescuento(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getEnvio(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                pedido.getTotal(),
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "PENDIENTE_PAGO",
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                items);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}