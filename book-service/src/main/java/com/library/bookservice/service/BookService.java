// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.service.
package com.library.bookservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.dto.BookRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.dto.BookResponseDTO;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Declara el tipo BookService y su contrato.
public interface BookService {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    BookResponseDTO createBook(BookRequestDTO request);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<BookResponseDTO> getAllBooks();

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    BookResponseDTO getBookById(Long id);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    BookResponseDTO updateBook(Long id, BookRequestDTO request);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteBook(Long id);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}