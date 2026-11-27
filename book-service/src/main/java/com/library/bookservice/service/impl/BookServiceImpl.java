// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.service.impl.
package com.library.bookservice.service.impl;

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
import com.library.bookservice.service.BookService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo BookServiceImpl y su contrato.
public class BookServiceImpl implements BookService {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static final Logger logger =
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            LoggerFactory.getLogger(BookServiceImpl.class);

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final BookRepository bookRepository;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private BookResponseDTO mapToResponse(Book book) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        BookResponseDTO response = new BookResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(book.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setTitle(book.getTitle());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setAuthor(book.getAuthor());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setPrice(book.getPrice());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setStock(book.getStock());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return response;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public BookResponseDTO createBook(BookRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Creando libro con título {}", request.getTitle());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Book book = new Book();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setTitle(request.getTitle());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setAuthor(request.getAuthor());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setPrice(request.getPrice());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setStock(request.getStock());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Book savedBook = bookRepository.save(book);

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Libro creado correctamente con id {}", savedBook.getId());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(savedBook);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<BookResponseDTO> getAllBooks() {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Listando todos los libros");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return bookRepository.findAll()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .stream()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .map(this::mapToResponse)
                // Nota de lectura: Encadena la operación toList al resultado anterior.
                .toList();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public BookResponseDTO getBookById(Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Buscando libro con id {}", id);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Book book = bookRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con id " + id));

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(book);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public BookResponseDTO updateBook(Long id, BookRequestDTO request) {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Book book = bookRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con id " + id));

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setTitle(request.getTitle());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setAuthor(request.getAuthor());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setPrice(request.getPrice());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        book.setStock(request.getStock());

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Actualizando libro con ID: {}", id);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Book updatedBook = bookRepository.save(book);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(updatedBook);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void deleteBook(Long id) {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Book book = bookRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con id " + id));

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Eliminando libro con ID: {}", id);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        bookRepository.delete(book);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}