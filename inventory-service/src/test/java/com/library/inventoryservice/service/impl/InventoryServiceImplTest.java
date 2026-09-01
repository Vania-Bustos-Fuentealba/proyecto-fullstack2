package com.library.inventoryservice.service.impl;

import com.library.inventoryservice.dto.InventoryRequestDTO;
import com.library.inventoryservice.dto.InventoryResponseDTO;
import com.library.inventoryservice.entity.Inventory;
import com.library.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private InventoryRequestDTO buildRequestDTO() {
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setBookId(10L);
        dto.setStock(25);
        return dto;
    }

    private Inventory buildInventory(Long id, Long bookId, Integer stock) {
        Inventory inventory = new Inventory();
        inventory.setId(id);
        inventory.setBookId(bookId);
        inventory.setStock(stock);
        return inventory;
    }

    @Test
    @DisplayName("Debe crear inventario correctamente")
    void shouldCreateInventorySuccessfully() {
        InventoryRequestDTO request = buildRequestDTO();
        Inventory savedInventory = buildInventory(1L, 10L, 25);

        when(restTemplate.getForObject("http://localhost:8082/books/10", String.class))
                .thenReturn("{\"id\":10,\"title\":\"Libro test\"}");
        when(inventoryRepository.existsByBookId(10L)).thenReturn(false);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(savedInventory);

        InventoryResponseDTO response = inventoryService.createInventory(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10L, response.getBookId());
        assertEquals(25, response.getStock());

        verify(restTemplate, times(1))
                .getForObject("http://localhost:8082/books/10", String.class);
        verify(inventoryRepository, times(1)).existsByBookId(10L);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si ya existe inventario para el libro")
    void shouldThrowExceptionWhenInventoryAlreadyExists() {
        InventoryRequestDTO request = buildRequestDTO();

        when(restTemplate.getForObject("http://localhost:8082/books/10", String.class))
                .thenReturn("{\"id\":10,\"title\":\"Libro test\"}");
        when(inventoryRepository.existsByBookId(10L)).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> inventoryService.createInventory(request)
        );

        assertEquals("Ya existe inventario para ese libro", exception.getMessage());

        verify(restTemplate, times(1))
                .getForObject("http://localhost:8082/books/10", String.class);
        verify(inventoryRepository, times(1)).existsByBookId(10L);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    @DisplayName("Debe obtener inventario por bookId")
    void shouldReturnInventoryByBookId() {
        Inventory inventory = buildInventory(1L, 10L, 25);

        when(inventoryRepository.findByBookId(10L)).thenReturn(Optional.of(inventory));

        InventoryResponseDTO response = inventoryService.getInventoryByBookId(10L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10L, response.getBookId());
        assertEquals(25, response.getStock());

        verify(inventoryRepository, times(1)).findByBookId(10L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando no existe inventario para el libro")
    void shouldThrowExceptionWhenInventoryNotFoundByBookId() {
        when(inventoryRepository.findByBookId(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> inventoryService.getInventoryByBookId(99L)
        );

        assertEquals("Inventario no encontrado para el libro con id 99", exception.getMessage());
        verify(inventoryRepository, times(1)).findByBookId(99L);
    }
}