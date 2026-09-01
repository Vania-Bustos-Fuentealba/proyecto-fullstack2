package com.library.inventoryservice.repository;

import com.library.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByBookId(Long bookId);

    boolean existsByBookId(Long bookId);
}