package com.library.cartservice.model;

import com.library.cartservice.dto.CartRequestDTO;
import com.library.cartservice.dto.CartResponseDTO;
import com.library.cartservice.entity.Cart;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CartModelTest {

    @Test
    void cartEntityShouldStoreValues() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUserId(2L);
        cart.setBookId(3L);
        cart.setQuantity(4);

        assertEquals(1L, cart.getId());
        assertEquals(2L, cart.getUserId());
        assertEquals(3L, cart.getBookId());
        assertEquals(4, cart.getQuantity());
    }

    @Test
    void cartRequestDtoShouldStoreValues() {
        CartRequestDTO request = new CartRequestDTO();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setQuantity(3);

        assertEquals(1L, request.getUserId());
        assertEquals(2L, request.getBookId());
        assertEquals(3, request.getQuantity());
    }

    @Test
    void cartResponseDtoShouldStoreValuesWithAllArgsConstructor() {
        CartResponseDTO response = new CartResponseDTO(1L, 2L, 3L, 4);

        assertEquals(1L, response.getId());
        assertEquals(2L, response.getUserId());
        assertEquals(3L, response.getBookId());
        assertEquals(4, response.getQuantity());
    }

    @Test
    void cartResponseDtoShouldStoreValuesWithNoArgsConstructor() {
        CartResponseDTO response = new CartResponseDTO();
        response.setId(1L);
        response.setUserId(2L);
        response.setBookId(3L);
        response.setQuantity(4);

        assertEquals(1L, response.getId());
        assertEquals(2L, response.getUserId());
        assertEquals(3L, response.getBookId());
        assertEquals(4, response.getQuantity());
    }

    @Test
    void cartRequestDtoShouldPassValidationWhenValid() {
        CartRequestDTO request = new CartRequestDTO();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setQuantity(1);

        Validator validator = buildValidator();

        Set<ConstraintViolation<CartRequestDTO>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void cartRequestDtoShouldFailValidationWhenInvalid() {
        CartRequestDTO request = new CartRequestDTO();
        request.setQuantity(0);

        Validator validator = buildValidator();

        Set<ConstraintViolation<CartRequestDTO>> violations = validator.validate(request);

        assertEquals(3, violations.size());
    }

    private Validator buildValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
}