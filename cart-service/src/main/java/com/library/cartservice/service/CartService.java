package com.library.cartservice.service;

import com.library.cartservice.dto.CartRequestDTO;
import com.library.cartservice.dto.CartResponseDTO;
import com.library.cartservice.entity.Cart;

import java.util.List;

public interface CartService {

    CartResponseDTO addItem(CartRequestDTO request);

    List<CartResponseDTO> getItemsByUser(Long userId);

    void deleteCart(Long id);
}
