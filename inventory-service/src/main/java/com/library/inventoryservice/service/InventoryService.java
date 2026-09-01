package com.library.inventoryservice.service;

import com.library.inventoryservice.dto.InventoryRequestDTO;
import com.library.inventoryservice.dto.InventoryResponseDTO;

public interface InventoryService {

    InventoryResponseDTO createInventory(InventoryRequestDTO request);

    InventoryResponseDTO getInventoryByBookId(Long bookId);
}