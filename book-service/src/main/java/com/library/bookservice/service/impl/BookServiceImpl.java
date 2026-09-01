package com.library.bookservice.service.impl;

import com.library.bookservice.dto.BookRequestDTO;
import com.library.bookservice.dto.BookResponseDTO;
import com.library.bookservice.entity.Book;
import com.library.bookservice.exceptions.BookNotFoundException;
import com.library.bookservice.repository.BookRepository;
import com.library.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Logger logger =
            LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    private BookResponseDTO mapToResponse(Book book) {
        BookResponseDTO response = new BookResponseDTO();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setPrice(book.getPrice());
        response.setStock(book.getStock());
        return response;
    }

    @Override
    public BookResponseDTO createBook(BookRequestDTO request) {
        logger.info("Creando libro con título {}", request.getTitle());

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());

        Book savedBook = bookRepository.save(book);

        logger.info("Libro creado correctamente con id {}", savedBook.getId());
        return mapToResponse(savedBook);
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        logger.info("Listando todos los libros");
        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        logger.info("Buscando libro con id {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con id " + id));

        return mapToResponse(book);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con id " + id));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());

        logger.info("Actualizando libro con ID: {}", id);

        Book updatedBook = bookRepository.save(book);
        return mapToResponse(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con id " + id));

        logger.info("Eliminando libro con ID: {}", id);
        bookRepository.delete(book);
    }
}