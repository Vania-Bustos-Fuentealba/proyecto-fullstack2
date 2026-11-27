// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.
package com.library.bookservice;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.dto.BookRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.Validation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;

// Nota de lectura: Declara el tipo BookValidationTest y su contrato.
class BookValidationTest {
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void precioYStockSonObligatoriosTambienEnBackend() {
        // Nota de lectura: Agrupa operaciones que pueden fallar para poder gestionar sus errores.
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            BookRequestDTO book = new BookRequestDTO();
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            book.setTitle("Libro válido"); book.setAuthor("Autora válida");
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            var violations = factory.getValidator().validate(book);
            // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
            assertEquals(2, violations.size());
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            book.setPrice(12.5); book.setStock(0);
            // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
            assertTrue(factory.getValidator().validate(book).isEmpty());
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            book.setStock(-1);
            // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
            assertFalse(factory.getValidator().validate(book).isEmpty());
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
