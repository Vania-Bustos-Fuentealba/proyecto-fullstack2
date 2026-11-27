// Nota de lectura: Ubica la clase dentro del paquete com.library.cartservice.service.
package com.library.cartservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.entity.Cart;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Declara el tipo CartService y su contrato.
public interface CartService {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    CartResponseDTO addItem(CartRequestDTO request);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<CartResponseDTO> getItemsByUser(Long userId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteCart(Long id);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
