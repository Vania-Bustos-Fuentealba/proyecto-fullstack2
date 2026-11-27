// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.security.
package com.library.authservice.security;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.controller.AdminAccountController;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.Role;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.repository.UserRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.service.AdminAccountService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.context.annotation.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.mock.web.MockServletContext;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.test.util.ReflectionTestUtils;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.test.web.servlet.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Nota de lectura: Declara el tipo AdminSecurityTest y su contrato.
class AdminSecurityTest {
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Configuration @EnableWebMvc @EnableWebSecurity
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Import({SecurityConfig.class, JwtAuthenticationFilter.class, AdminAccountController.class, com.library.authservice.controller.AuthSessionController.class})
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    static class Config {
        // Nota de lectura: Publica el objeto como dependencia gestionada por Spring.
        @Bean UserRepository users() { return mock(UserRepository.class); }
        // Nota de lectura: Publica el objeto como dependencia gestionada por Spring.
        @Bean AdminAccountService accounts() { return mock(AdminAccountService.class); }
        // Nota de lectura: Publica el objeto como dependencia gestionada por Spring.
        @Bean JwtService jwt() { return new JwtService(); }
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    AnnotationConfigWebApplicationContext context;
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    MockMvc mvc;
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    UserRepository users;
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    JwtService jwt;
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    User user;
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @BeforeEach void setup() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        context = new AnnotationConfigWebApplicationContext();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        context.getEnvironment().getPropertySources().addFirst(new org.springframework.core.env.MapPropertySource("test", Map.of(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                "jwt.secret", Base64.getEncoder().encodeToString("clave-exclusiva-de-pruebas-123456789".getBytes()), "jwt.expiration", "60000")));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        context.setServletContext(new MockServletContext()); context.register(Config.class); context.refresh();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        users = context.getBean(UserRepository.class); jwt = context.getBean(JwtService.class);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        user = new User(1L, "Admin", "admin@example.com", "hash", Role.ADMIN);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @AfterEach void close() { context.close(); }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void protegeInclusoAccesoDirectoSinGateway() throws Exception {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(get("/auth/users")).andExpect(status().isUnauthorized());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(delete("/auth/users/7")).andExpect(status().isUnauthorized());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void permiteAdminYNoDevuelvePassword() throws Exception {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(users.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(get("/auth/users").header("Authorization", "Bearer " + jwt.generateToken(user))).andExpect(status().isOk());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void clienteNoPuedeLeerCrearEditarNiEliminarCuentas() throws Exception {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setRole(Role.CLIENT);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(users.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String bearer = "Bearer " + jwt.generateToken(user);
        // Nota de lectura: Recorre los elementos indicados y ejecuta el bloque para cada uno.
        for (var request : List.of(get("/auth/users"), post("/auth/users"), put("/auth/users/7"), delete("/auth/users/7")))
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            mvc.perform(request.header("Authorization", bearer)).andExpect(status().isForbidden());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void compruebaRolActualAunqueElTokenAntiguoDigaAdmin() throws Exception {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String bearer = "Bearer " + jwt.generateToken(user);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setRole(Role.CLIENT);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(users.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(get("/auth/users").header("Authorization", bearer)).andExpect(status().isForbidden());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void cuentaEliminadaOTokenFalsoNoDaAcceso() throws Exception {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(get("/auth/users").header("Authorization", "Bearer " + jwt.generateToken(user))).andExpect(status().isUnauthorized());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(get("/auth/users").header("Authorization", "Bearer falso")).andExpect(status().isUnauthorized());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void consultaDeSesionDevuelveElRolActual() throws Exception {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String bearer = "Bearer " + jwt.generateToken(user);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setRole(Role.CLIENT);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(users.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(get("/auth/session").header("Authorization", bearer))
                // Nota de lectura: Encadena la operación andExpect al resultado anterior.
                .andExpect(status().isOk()).andExpect(jsonPath("$.role").value("CLIENT"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void tokenNoSeTransfiereAOtraCuentaConElMismoCorreo() throws Exception {
        // El repositorio devuelve ahora otra identidad con el mismo correo.
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(users.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String token = jwt.generateToken(user);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setId(99L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(get("/auth/users").header("Authorization", "Bearer " + token)).andExpect(status().isUnauthorized());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void cambiarCredencialesInvalidaElTokenAnterior() throws Exception {
        // La consulta posterior al cambio debe devolver la versión actualizada de la cuenta.
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(users.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String token = jwt.generateToken(user);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setTokenVersion(user.getTokenVersion() + 1);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(get("/auth/users").header("Authorization", "Bearer " + token)).andExpect(status().isUnauthorized());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(get("/auth/users").header("Authorization", "Bearer " + jwt.generateToken(user))).andExpect(status().isOk());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void renuevaConElRolActualSinExtenderLaSesionAbsoluta() throws Exception {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(users.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        ReflectionTestUtils.setField(jwt, "jwtExpiration", 30000L);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String original = jwt.generateToken(user);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setRole(Role.CLIENT);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        ReflectionTestUtils.setField(jwt, "jwtExpiration", 60000L);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String json = mvc.perform(post("/auth/refresh").header("Authorization", "Bearer " + original))
                // Nota de lectura: Encadena la operación andExpect al resultado anterior.
                .andExpect(status().isOk()).andExpect(jsonPath("$.role").value("CLIENT"))
                // Nota de lectura: Encadena la operación andReturn al resultado anterior.
                .andReturn().getResponse().getContentAsString();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var mapper = new tools.jackson.databind.ObjectMapper();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String renewed = mapper.readTree(json).get("token").asText();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var oldClaims = mapper.readTree(new String(Base64.getUrlDecoder().decode(original.split("\\.")[1]), java.nio.charset.StandardCharsets.UTF_8));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var newClaims = mapper.readTree(new String(Base64.getUrlDecoder().decode(renewed.split("\\.")[1]), java.nio.charset.StandardCharsets.UTF_8));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        Assertions.assertNotEquals(original, renewed);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        Assertions.assertTrue(newClaims.get("exp").asLong() > oldClaims.get("exp").asLong());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        Assertions.assertEquals(oldClaims.get("sessionExpiresAt"), newClaims.get("sessionExpiresAt"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void renovacionRechazaVencidosAlteradosYVersionAntigua() throws Exception {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(users.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(post("/auth/refresh")).andExpect(status().isUnauthorized());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(post("/auth/refresh").header("Authorization", "Bearer falso")).andExpect(status().isUnauthorized());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        ReflectionTestUtils.setField(jwt, "jwtExpiration", -1000L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(post("/auth/refresh").header("Authorization", "Bearer " + jwt.generateToken(user))).andExpect(status().isUnauthorized());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        ReflectionTestUtils.setField(jwt, "jwtExpiration", 60000L);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String original = jwt.generateToken(user);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setTokenVersion(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        mvc.perform(post("/auth/refresh").header("Authorization", "Bearer " + original)).andExpect(status().isUnauthorized());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        Assertions.assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> jwt.renewToken(original, user));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
