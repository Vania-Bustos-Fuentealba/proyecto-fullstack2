// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.impl.
package com.library.bookservice.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.dto.BookRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.dto.BookResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.entity.Book;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.exceptions.BookNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.repository.BookRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.service.impl.BookServiceImpl;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.DisplayName;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.extension.ExtendWith;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.InjectMocks;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.Mock;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.junit.jupiter.MockitoExtension;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.test.context.ActiveProfiles;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ActiveProfiles("test")
// Nota de lectura: Declara el tipo BookServiceImplTest y su contrato.
class BookServiceImplTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private BookRepository bookRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private BookServiceImpl bookService;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private BookRequestDTO buildRequestDTO() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        BookRequestDTO dto = new BookRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setTitle("Clean Code");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setAuthor("Robert Martin");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setPrice(29.99);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setStock(10);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return dto;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private Book buildBook(Long id, String title, String author, Double price, Integer stock) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Book book = new Book();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setId(id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setTitle(title);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setAuthor(author);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setPrice(price);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setStock(stock);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return book;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe crear un libro correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldCreateBookSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        BookRequestDTO request = buildRequestDTO();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Book savedBook = buildBook(1L, "Clean Code", "Robert Martin", 29.99, 10);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        BookResponseDTO response = bookService.createBook(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Clean Code", response.getTitle());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Robert Martin", response.getAuthor());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(29.99, response.getPrice());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10, response.getStock());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, times(1)).save(any(Book.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe listar todos los libros")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnAllBooks() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Book book1 = buildBook(1L, "Clean Code", "Robert Martin", 29.99, 10);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Book book2 = buildBook(2L, "Effective Java", "Joshua Bloch", 39.99, 5);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<BookResponseDTO> response = bookService.getAllBooks();

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, response.size());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Clean Code", response.get(0).getTitle());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Effective Java", response.get(1).getTitle());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, times(1)).findAll();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe obtener un libro por id")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnBookById() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Book book = buildBook(1L, "Clean Code", "Robert Martin", 29.99, 10);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        BookResponseDTO response = bookService.getBookById(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Clean Code", response.getTitle());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, times(1)).findById(1L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción al buscar un libro inexistente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenBookNotFoundById() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        BookNotFoundException exception = assertThrows(
                // Nota de lectura: Aporta el valor BookNotFoundException.class a la declaración o llamada abierta.
                BookNotFoundException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> bookService.getBookById(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Libro no encontrado con id 99", exception.getMessage());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, times(1)).findById(99L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe actualizar un libro correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldUpdateBookSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        BookRequestDTO request = buildRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setTitle("Clean Code 2");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setPrice(34.99);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setStock(5);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Book existingBook = buildBook(1L, "Clean Code", "Robert Martin", 29.99, 10);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Book updatedBook = buildBook(1L, "Clean Code 2", "Robert Martin", 34.99, 5);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        BookResponseDTO response = bookService.updateBook(1L, request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Clean Code 2", response.getTitle());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(34.99, response.getPrice());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(5, response.getStock());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, times(1)).findById(1L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, times(1)).save(any(Book.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción al actualizar un libro inexistente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenUpdatingNonExistingBook() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        BookRequestDTO request = buildRequestDTO();

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        BookNotFoundException exception = assertThrows(
                // Nota de lectura: Aporta el valor BookNotFoundException.class a la declaración o llamada abierta.
                BookNotFoundException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> bookService.updateBook(99L, request)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Libro no encontrado con id 99", exception.getMessage());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, times(1)).findById(99L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, never()).save(any(Book.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe eliminar un libro correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldDeleteBookSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Book existingBook = buildBook(1L, "Clean Code", "Robert Martin", 29.99, 10);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        doNothing().when(bookRepository).delete(existingBook);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertDoesNotThrow(() -> bookService.deleteBook(1L));

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, times(1)).findById(1L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, times(1)).delete(existingBook);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción al eliminar un libro inexistente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenDeletingNonExistingBook() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        BookNotFoundException exception = assertThrows(
                // Nota de lectura: Aporta el valor BookNotFoundException.class a la declaración o llamada abierta.
                BookNotFoundException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> bookService.deleteBook(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Libro no encontrado con id 99", exception.getMessage());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, times(1)).findById(99L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(bookRepository, never()).delete(any(Book.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}