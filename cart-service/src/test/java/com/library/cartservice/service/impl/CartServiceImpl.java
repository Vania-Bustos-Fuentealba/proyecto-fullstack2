package com.library.cartservice.service.impl;

import com.library.cartservice.dto.CartRequestDTO;
import com.library.cartservice.dto.CartResponseDTO;
import com.library.cartservice.entity.Cart;
import com.library.cartservice.exceptions.CartNotFoundException;
import com.library.cartservice.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void addItemShouldSaveAndReturnResponseWhenBookExistsAndItemDoesNotExist() {
        CartRequestDTO request = new CartRequestDTO();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setQuantity(3);

        when(restTemplate.getForObject("http://book-service:8080/books/2", String.class))
                .thenReturn("{\"id\":2,\"title\":\"Libro Test\"}");

        when(cartRepository.existsByUserIdAndBookId(1L, 2L))
                .thenReturn(false);

        when(cartRepository.save(any(Cart.class)))
                .thenAnswer(invocation -> {
                    Cart cart = invocation.getArgument(0);
                    cart.setId(10L);
                    return cart;
                });

        CartResponseDTO response = cartService.addItem(request);

        assertEquals(10L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals(2L, response.getBookId());
        assertEquals(3, response.getQuantity());

        verify(restTemplate).getForObject("http://book-service:8080/books/2", String.class);
        verify(cartRepository).existsByUserIdAndBookId(1L, 2L);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void addItemShouldThrowExceptionWhenBookIsAlreadyInCart() {
        CartRequestDTO request = new CartRequestDTO();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setQuantity(1);

        when(restTemplate.getForObject("http://book-service:8080/books/2", String.class))
                .thenReturn("{\"id\":2}");

        when(cartRepository.existsByUserIdAndBookId(1L, 2L))
                .thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.addItem(request));

        assertEquals("El libro ya está en el carrito de este usuario", exception.getMessage());

        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void addItemShouldThrowExceptionWhenBookServiceFails() {
        CartRequestDTO request = new CartRequestDTO();
        request.setUserId(1L);
        request.setBookId(999L);
        request.setQuantity(1);

        when(restTemplate.getForObject("http://book-service:8080/books/999", String.class))
                .thenThrow(new RuntimeException("Libro no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.addItem(request));

        assertEquals("Libro no encontrado", exception.getMessage());

        verify(cartRepository, never()).existsByUserIdAndBookId(anyLong(), anyLong());
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void getItemsByUserShouldReturnCartItems() {
        Cart cartOne = new Cart();
        cartOne.setId(1L);
        cartOne.setUserId(1L);
        cartOne.setBookId(10L);
        cartOne.setQuantity(2);

        Cart cartTwo = new Cart();
        cartTwo.setId(2L);
        cartTwo.setUserId(1L);
        cartTwo.setBookId(11L);
        cartTwo.setQuantity(1);

        when(cartRepository.findByUserId(1L))
                .thenReturn(List.of(cartOne, cartTwo));

        List<CartResponseDTO> response = cartService.getItemsByUser(1L);

        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals(10L, response.get(0).getBookId());
        assertEquals(2, response.get(0).getQuantity());
        assertEquals(2L, response.get(1).getId());
        assertEquals(11L, response.get(1).getBookId());
        assertEquals(1, response.get(1).getQuantity());

        verify(cartRepository).findByUserId(1L);
    }

    @Test
    void deleteCartShouldDeleteWhenCartExists() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUserId(1L);
        cart.setBookId(2L);
        cart.setQuantity(1);

        when(cartRepository.findById(1L))
                .thenReturn(Optional.of(cart));

        cartService.deleteCart(1L);

        verify(cartRepository).findById(1L);
        verify(cartRepository).delete(cart);
    }

    @Test
    void deleteCartShouldThrowExceptionWhenCartDoesNotExist() {
        when(cartRepository.findById(999L))
                .thenReturn(Optional.empty());

        CartNotFoundException exception = assertThrows(CartNotFoundException.class, () -> cartService.deleteCart(999L));

        assertEquals("Item de carrito no encontrado con id 999", exception.getMessage());

        verify(cartRepository).findById(999L);
        verify(cartRepository, never()).delete(any(Cart.class));
    }
}