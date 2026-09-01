package com.library.cartservice.controller;

import com.library.cartservice.dto.CartRequestDTO;
import com.library.cartservice.dto.CartResponseDTO;
import com.library.cartservice.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Test
    void healthShouldReturnServiceMessage() {
        String response = cartController.health();

        assertEquals("cart service funcionando correctamente", response);
    }

    @Test
    void addItemShouldReturnCreatedResponse() {
        CartRequestDTO request = new CartRequestDTO();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setQuantity(1);

        CartResponseDTO serviceResponse = new CartResponseDTO(10L, 1L, 2L, 1);

        when(cartService.addItem(request))
                .thenReturn(serviceResponse);

        ResponseEntity<CartResponseDTO> response = cartController.addItem(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getId());
        assertEquals(1L, response.getBody().getUserId());
        assertEquals(2L, response.getBody().getBookId());
        assertEquals(1, response.getBody().getQuantity());

        verify(cartService).addItem(request);
    }

    @Test
    void getItemsByUserShouldReturnOkResponse() {
        List<CartResponseDTO> items = List.of(
                new CartResponseDTO(1L, 1L, 2L, 1),
                new CartResponseDTO(2L, 1L, 3L, 2)
        );

        when(cartService.getItemsByUser(1L))
                .thenReturn(items);

        ResponseEntity<List<CartResponseDTO>> response = cartController.getItemsByUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(2L, response.getBody().get(0).getBookId());
        assertEquals(3L, response.getBody().get(1).getBookId());

        verify(cartService).getItemsByUser(1L);
    }

    @Test
    void deleteCartShouldReturnNoContentResponse() {
        ResponseEntity<Void> response = cartController.deleteCart(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(cartService).deleteCart(1L);
    }
}