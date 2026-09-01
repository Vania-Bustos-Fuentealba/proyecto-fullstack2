package com.library.cartservice.service.impl;

import com.library.cartservice.dto.CartRequestDTO;
import com.library.cartservice.dto.CartResponseDTO;
import com.library.cartservice.entity.Cart;
import com.library.cartservice.exceptions.CartNotFoundException;
import com.library.cartservice.repository.CartRepository;
import com.library.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final RestTemplate restTemplate;

    private static final Logger logger =
            LoggerFactory.getLogger(CartServiceImpl.class);

    private CartResponseDTO mapToResponse(Cart cart) {
        CartResponseDTO response = new CartResponseDTO();
        response.setId(cart.getId());
        response.setUserId(cart.getUserId());
        response.setBookId(cart.getBookId());
        response.setQuantity(cart.getQuantity());
        return response;
    }

    @Override
    public CartResponseDTO addItem(CartRequestDTO request) {
        logger.info("Agregando item al carrito para userId {}", request.getUserId());

        String responseBook = restTemplate.getForObject(
                "http://localhost:8082/books/" + request.getBookId(),
                String.class
        );

        logger.info("Respuesta desde book-service: {}", responseBook);

        if (cartRepository.existsByUserIdAndBookId(request.getUserId(), request.getBookId())) {
            throw new RuntimeException("El libro ya está en el carrito de este usuario");
        }

        Cart cart = new Cart();
        cart.setUserId(request.getUserId());
        cart.setBookId(request.getBookId());
        cart.setQuantity(request.getQuantity());

        Cart saved = cartRepository.save(cart);
        logger.info("Agregado correctamente con id {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public List<CartResponseDTO> getItemsByUser(Long userId) {
        logger.info("Listando carrito del userId {}", userId);
        return cartRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteCart(Long id) {
        logger.info("Eliminando item del carrito con id {}", id);

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Item con id {} no encontrado", id);
                    return new CartNotFoundException("Item de carrito no encontrado con id " + id);
                });

        cartRepository.delete(cart);
        logger.info("Item con id {} eliminado correctamente", id);
    }
}