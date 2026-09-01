package com.library.bookservice.service;

import com.library.bookservice.dto.BookRequestDTO;
import com.library.bookservice.dto.BookResponseDTO;

import java.util.List;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO request);

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO getBookById(Long id);

    BookResponseDTO updateBook(Long id, BookRequestDTO request);

    void deleteBook(Long id);
}