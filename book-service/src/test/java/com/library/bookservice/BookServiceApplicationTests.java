// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.
package com.library.bookservice;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.boot.test.context.SpringBootTest;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;

// HTTP real + persistencia H2 para ejecutar verify sin una BD externa.
// Las migraciones MySQL se comprueban además en el recorrido Docker de entrega.
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
    useMainMethod = SpringBootTest.UseMainMethod.ALWAYS, properties = {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "spring.datasource.url=jdbc:h2:mem:book;MODE=MySQL;DB_CLOSE_DELAY=-1",
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "spring.datasource.driver-class-name=org.h2.Driver",
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "spring.datasource.username=sa",
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "spring.datasource.password=",
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "spring.jpa.hibernate.ddl-auto=create-drop",
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "spring.flyway.enabled=false",
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "spring.jpa.show-sql=false",
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "eureka.client.enabled=false",
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "spring.cloud.discovery.enabled=false",
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "JWT_SECRET=aW50ZWdyYXRpb24tdGVzdC1rZXktMzItYnl0ZXMtb25seQ==",
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        "ADMIN_INITIAL_PASSWORD=integration-test-password"})
// Nota de lectura: Declara el tipo BookServiceApplicationTests y su contrato.
class BookServiceApplicationTests {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @org.springframework.boot.test.web.server.LocalServerPort int port;
    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
    java.net.http.HttpClient http = java.net.http.HttpClient.newHttpClient();
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    java.net.http.HttpResponse<String> call(String method, String path, String body, String... headers) throws Exception {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var b = java.net.http.HttpRequest.newBuilder(java.net.URI.create("http://localhost:" + port + path))
                // Nota de lectura: Encadena la operación header al resultado anterior.
                .header("Content-Type", "application/json");
        // Nota de lectura: Recorre los elementos indicados y ejecuta el bloque para cada uno.
        for (int i=0; i<headers.length; i+=2) b.header(headers[i], headers[i+1]);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return http.send(b.method(method, body == null ? java.net.http.HttpRequest.BodyPublishers.noBody()
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                : java.net.http.HttpRequest.BodyPublishers.ofString(body)).build(), java.net.http.HttpResponse.BodyHandlers.ofString());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    long id(java.net.http.HttpResponse<String> r) { return ((Number) com.jayway.jsonpath.JsonPath.read(r.body(), "$.id")).longValue(); }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void crudYCheckoutConStockAtomico() throws Exception {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String book = "{\"title\":\"Libro HTTP\",\"author\":\"Autora\",\"price\":14990.50,\"stock\":3}";
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        var created = call("POST", "/books", book); assertEquals(201, created.statusCode()); long bid=id(created);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200, call("GET", "/books", null).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200, call("GET", "/books/health", null).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200, call("GET", "/books/"+bid, null).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200, call("PUT", "/books/"+bid, book).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(400, call("POST", "/books", "{}").statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(404, call("GET", "/books/999999", null).statusCode());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String order = "{\"cliente\":\"Ana Perez\",\"correo\":\"ana@gmail.com\",\"direccion\":\"Calle 123\",\"comuna\":\"Santiago\",\"metodoPago\":\"transferencia\",\"cupon\":\"DUOC10\",\"items\":[{\"libroId\":"+bid+",\"cantidad\":2}]}";
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String[] headers={"X-User-Id","ana@gmail.com","X-User-Role","CLIENT","Idempotency-Key","http-test-request-0001"};
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        var ordered=call("POST", "/pedidos", order, headers); assertEquals(201,ordered.statusCode(),ordered.body());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        long oid=id(ordered);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("PENDIENTE_PAGO",com.jayway.jsonpath.JsonPath.read(ordered.body(),"$.estado"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(30973.0, ((Number)com.jayway.jsonpath.JsonPath.read(ordered.body(),"$.total")).doubleValue());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(oid,id(call("POST","/pedidos",order,headers)));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var left=call("GET","/books/"+bid,null);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1,((Number)com.jayway.jsonpath.JsonPath.read(left.body(),"$.stock")).intValue());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200,call("GET","/pedidos/"+oid,null,headers).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(403,call("GET","/pedidos/"+oid,null,"X-User-Id","otra@gmail.com","X-User-Role","CLIENT").statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200,call("GET","/pedidos",null,headers).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(409,call("POST","/pedidos",order.replace("Ana Perez","Otra persona"),headers).statusCode());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        headers[5]="http-test-request-0002";
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(409,call("POST","/pedidos",order,headers).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1,((Number)com.jayway.jsonpath.JsonPath.read(call("GET","/books/"+bid,null).body(),"$.stock")).intValue());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var disposable=call("POST","/books",book);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(204,call("DELETE","/books/"+id(disposable),null).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(409,call("DELETE","/books/"+bid,null).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(404,call("GET","/ruta-inexistente",null).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200,call("GET","/v3/api-docs",null).statusCode());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
