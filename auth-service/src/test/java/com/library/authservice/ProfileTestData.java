// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.
package com.library.authservice;
// Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
public final class ProfileTestData {
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public static <T extends com.library.authservice.dto.ProfileRequest> T complete(T request) {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setRun("123456785");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setLastName("Prueba");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBirthDate(java.time.LocalDate.parse("1998-05-12"));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setRegion("Metropolitana de Santiago");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setCommune("Santiago");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setAddress("Calle de prueba 123");
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return request;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
