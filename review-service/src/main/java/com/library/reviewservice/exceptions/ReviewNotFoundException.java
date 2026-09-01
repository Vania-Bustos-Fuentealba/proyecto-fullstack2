package com.library.reviewservice.exceptions;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(String message) {

        super(message);
    }
}
