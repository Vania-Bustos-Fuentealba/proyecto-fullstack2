// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.
package com.library.authservice;
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
        "spring.datasource.url=jdbc:h2:mem:auth;MODE=MySQL;DB_CLOSE_DELAY=-1",
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
// Nota de lectura: Declara el tipo AuthServiceApplicationTests y su contrato.
class AuthServiceApplicationTests {

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

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @org.springframework.beans.factory.annotation.Autowired org.springframework.web.client.RestTemplate remote;
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void autenticacionYAdministracionHttp() throws Exception {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var server=org.springframework.test.web.client.MockRestServiceServer.bindTo(remote).ignoreExpectOrder(true).build();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        server.expect(org.springframework.test.web.client.ExpectedCount.manyTimes(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo(org.hamcrest.Matchers.startsWith("http://user-service:8080/users")))
                // Nota de lectura: Encadena la operación andRespond al resultado anterior.
                .andRespond(org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess("{}",org.springframework.http.MediaType.APPLICATION_JSON));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var login=call("POST","/auth/login","{\"email\":\"admin@library.cl\",\"password\":\"integration-test-password\"}");
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200,login.statusCode(),login.body());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String jwt=com.jayway.jsonpath.JsonPath.read(login.body(),"$.token");
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String[] headers={"Authorization","Bearer "+jwt};
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200,call("GET","/auth/session",null,headers).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200,call("GET","/auth/users",null,headers).statusCode());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String data="{\"name\":\"Persona HTTP\",\"email\":\"http@gmail.com\",\"password\":\"password-test-123\",\"role\":\"CLIENT\"}";
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        var created=call("POST","/auth/users",data,headers); assertEquals(201,created.statusCode(),created.body()); long uid=id(created);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200,call("GET","/auth/users/"+uid,null,headers).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200,call("PUT","/auth/users/"+uid,data.replace("Persona HTTP","Persona Editada"),headers).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(409,call("POST","/auth/users",data,headers).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(400,call("POST","/auth/users","{}",headers).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(404,call("GET","/auth/users/999999",null,headers).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(204,call("DELETE","/auth/users/"+uid,null,headers).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(201,call("POST","/auth/register",data).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(401,call("POST","/auth/login","{\"email\":\"http@gmail.com\",\"password\":\"incorrecta\"}").statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(401,call("GET","/auth/users",null).statusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200,call("GET","/v3/api-docs",null).statusCode());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        server.verify();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
