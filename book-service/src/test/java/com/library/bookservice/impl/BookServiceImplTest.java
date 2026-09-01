package com.library.bookservice.impl;

import com.library.bookservice.dto.BookRequestDTO;
import com.library.bookservice.dto.BookResponseDTO;
import com.library.bookservice.entity.Book;
import com.library.bookservice.exceptions.BookNotFoundException;
import com.library.bookservice.repository.BookRepository;
import com.library.bookservice.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookRequestDTO buildRequestDTO() {
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitle("Clean Code");
        dto.setAuthor("Robert Martin");
        dto.setPrice(29.99);
        dto.setStock(10);
        return dto;
    }

    private Book buildBook(Long id, String title, String author, Double price, Integer stock) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(price);
        book.setStock(stock);
        return book;
    }

    @Test
    @DisplayName("Debe crear un libro correctamente")
    void shouldCreateBookSuccessfully() {
        BookRequestDTO request = buildRequestDTO();
        Book savedBook = buildBook(1L, "Clean Code", "Robert Martin", 29.99, 10);

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        BookResponseDTO response = bookService.createBook(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Clean Code", response.getTitle());
        assertEquals("Robert Martin", response.getAuthor());
        assertEquals(29.99, response.getPrice());
        assertEquals(10, response.getStock());

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Debe listar todos los libros")
    void shouldReturnAllBooks() {
        Book book1 = buildBook(1L, "Clean Code", "Robert Martin", 29.99, 10);
        Book book2 = buildBook(2L, "Effective Java", "Joshua Bloch", 39.99, 5);

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<BookResponseDTO> response = bookService.getAllBooks();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Clean Code", response.get(0).getTitle());
        assertEquals("Effective Java", response.get(1).getTitle());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe obtener un libro por id")
    void shouldReturnBookById() {
        Book book = buildBook(1L, "Clean Code", "Robert Martin", 29.99, 10);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponseDTO response = bookService.getBookById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Clean Code", response.getTitle());

        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar un libro inexistente")
    void shouldThrowExceptionWhenBookNotFoundById() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.getBookById(99L)
        );

        assertEquals("Libro no encontrado con id 99", exception.getMessage());
        verify(bookRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe actualizar un libro correctamente")
    void shouldUpdateBookSuccessfully() {
        BookRequestDTO request = buildRequestDTO();
        request.setTitle("Clean Code 2");
        request.setPrice(34.99);
        request.setStock(5);

        Book existingBook = buildBook(1L, "Clean Code", "Robert Martin", 29.99, 10);
        Book updatedBook = buildBook(1L, "Clean Code 2", "Robert Martin", 34.99, 5);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        BookResponseDTO response = bookService.updateBook(1L, request);

        assertNotNull(response);
        assertEquals("Clean Code 2", response.getTitle());
        assertEquals(34.99, response.getPrice());
        assertEquals(5, response.getStock());

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar un libro inexistente")
    void shouldThrowExceptionWhenUpdatingNonExistingBook() {
        BookRequestDTO request = buildRequestDTO();

        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.updateBook(99L, request)
        );

        assertEquals("Libro no encontrado con id 99", exception.getMessage());
        verify(bookRepository, times(1)).findById(99L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Debe eliminar un libro correctamente")
    void shouldDeleteBookSuccessfully() {
        Book existingBook = buildBook(1L, "Clean Code", "Robert Martin", 29.99, 10);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        doNothing().when(bookRepository).delete(existingBook);

        assertDoesNotThrow(() -> bookService.deleteBook(1L));

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(existingBook);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar un libro inexistente")
    void shouldThrowExceptionWhenDeletingNonExistingBook() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.deleteBook(99L)
        );

        assertEquals("Libro no encontrado con id 99", exception.getMessage());
        verify(bookRepository, times(1)).findById(99L);
        verify(bookRepository, never()).delete(any(Book.class));
    }
}