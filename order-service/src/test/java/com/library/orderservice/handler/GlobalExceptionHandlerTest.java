package com.library.orderservice.handler;

import com.library.orderservice.dto.OrderRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleValidationExceptionShouldReturnBadRequestResponse() {
        OrderRequestDTO request = new OrderRequestDTO();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(request, "orderRequestDTO");
        bindingResult.addError(new FieldError("orderRequestDTO", "userId", "El userId es obligatorio"));
        bindingResult.addError(new FieldError("orderRequestDTO", "bookId", "El bookId es obligatorio"));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, Object>> response = handler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("Error de validación", response.getBody().get("message"));

        Object errorsObject = response.getBody().get("errors");
        assertTrue(errorsObject instanceof Map);

        Map<?, ?> errors = (Map<?, ?>) errorsObject;
        assertEquals("El userId es obligatorio", errors.get("userId"));
        assertEquals("El bookId es obligatorio", errors.get("bookId"));
    }

    @Test
    void handleRuntimeExceptionShouldReturnBadRequestResponse() {
        RuntimeException exception = new RuntimeException("Usuario no encontrado");

        ResponseEntity<Map<String, Object>> response = handler.handleRuntimeException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("Error en la solicitud", response.getBody().get("message"));
        assertEquals("Usuario no encontrado", response.getBody().get("details"));
    }

    @Test
    void handleGeneralExceptionShouldReturnInternalServerErrorResponse() {
        Exception exception = new Exception("Error inesperado");

        ResponseEntity<Map<String, Object>> response = handler.handleGeneralException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().get("status"));
        assertEquals("Error interno del servidor", response.getBody().get("message"));
        assertEquals("Error inesperado", response.getBody().get("details"));
    }

    @Test
    void handleNoResourceFoundShouldReturnNotFoundResponse() {
        NoResourceFoundException exception = mock(NoResourceFoundException.class);

        when(exception.getMessage()).thenReturn("No static resource /orders/no-existe");

        ResponseEntity<Map<String, Object>> response = handler.handleNoResourceFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().get("status"));
        assertEquals("Recurso no encontrado", response.getBody().get("message"));
        assertEquals("No static resource /orders/no-existe", response.getBody().get("details"));
    }
}