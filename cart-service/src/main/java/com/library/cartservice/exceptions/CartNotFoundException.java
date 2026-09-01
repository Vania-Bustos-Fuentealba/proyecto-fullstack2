package com.library.cartservice.exceptions;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String message) {

        super(message);
    }
}
