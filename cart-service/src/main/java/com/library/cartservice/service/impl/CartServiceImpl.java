// Nota de lectura: Ubica la clase dentro del paquete com.library.cartservice.service.impl.
package com.library.cartservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.entity.Cart;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.exceptions.CartNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.repository.CartRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.service.CartService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo CartServiceImpl y su contrato.
public class CartServiceImpl implements CartService {

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final CartRepository cartRepository;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final RestTemplate restTemplate;

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static final Logger logger =
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            LoggerFactory.getLogger(CartServiceImpl.class);

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private CartResponseDTO mapToResponse(Cart cart) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartResponseDTO response = new CartResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(cart.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setUserId(cart.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setBookId(cart.getBookId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setQuantity(cart.getQuantity());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return response;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public CartResponseDTO addItem(CartRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Agregando item al carrito para userId {}", request.getUserId());

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        String responseBook = restTemplate.getForObject(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                "http://localhost:8082/books/" + request.getBookId(),
                // Nota de lectura: Aporta el valor String.class a la declaración o llamada abierta.
                String.class
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Respuesta desde book-service: {}", responseBook);

        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (cartRepository.existsByUserIdAndBookId(request.getUserId(), request.getBookId())) {
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new RuntimeException("El libro ya está en el carrito de este usuario");
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Cart cart = new Cart();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setUserId(request.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setBookId(request.getBookId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setQuantity(request.getQuantity());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Cart saved = cartRepository.save(cart);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Agregado correctamente con id {}", saved.getId());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(saved);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<CartResponseDTO> getItemsByUser(Long userId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Listando carrito del userId {}", userId);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return cartRepository.findByUserId(userId)
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .stream()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .map(this::mapToResponse)
                // Nota de lectura: Encadena la operación toList al resultado anterior.
                .toList();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void deleteCart(Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Eliminando item del carrito con id {}", id);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Cart cart = cartRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> {
                    // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
                    logger.warn("Item con id {} no encontrado", id);
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return new CartNotFoundException("Item de carrito no encontrado con id " + id);
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        cartRepository.delete(cart);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Item con id {} eliminado correctamente", id);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}