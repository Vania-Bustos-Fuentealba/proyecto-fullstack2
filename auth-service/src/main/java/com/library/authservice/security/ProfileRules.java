// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.security.
package com.library.authservice.security;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.ProfileRequest;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.User;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.server.ResponseStatusException;

// Una petición directa a la API debe respetar las mismas reglas que el formulario.
// Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
public final class ProfileRules {
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private ProfileRules() {}
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static final Set<String> LOCATIONS = loadLocations();
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static Set<String> loadLocations() {
        // Nota de lectura: Agrupa operaciones que pueden fallar para poder gestionar sus errores.
        try (var input = ProfileRules.class.getResourceAsStream("/regiones-comunas.tsv");
             // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
             var reader = new java.io.BufferedReader(new java.io.InputStreamReader(
                     // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                     Objects.requireNonNull(input), java.nio.charset.StandardCharsets.UTF_8))) {
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return new HashSet<>(reader.lines().toList());
        // Nota de lectura: Captura el error y elige una respuesta controlada en lugar de mostrar éxito.
        } catch (java.io.IOException ex) { throw new IllegalStateException("No se pudo cargar la relación región/comuna", ex); }
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public static boolean validRun(String value) {
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (value == null || !value.matches("[0-9]{6,8}[0-9Kk]")) return false;
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        int sum = 0, factor = 2;
        // Nota de lectura: Recorre los elementos indicados y ejecuta el bloque para cada uno.
        for (int i = value.length() - 2; i >= 0; i--) {
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            sum += (value.charAt(i) - '0') * factor;
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            factor = factor == 7 ? 2 : factor + 1;
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        int digit = 11 - sum % 11;
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        char expected = digit == 11 ? '0' : digit == 10 ? 'K' : (char) ('0' + digit);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return Character.toUpperCase(value.charAt(value.length() - 1)) == expected;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public static void validate(ProfileRequest request) {
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (!validRun(request.getRun())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El RUN no es válido");
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (!LOCATIONS.contains(request.getRegion() + "\t" + request.getCommune()))
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La comuna no pertenece a la región seleccionada");
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public static void copy(ProfileRequest source, ProfileRequest target) {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        target.setRun(source.getRun());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        target.setLastName(source.getLastName());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        target.setBirthDate(source.getBirthDate());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        target.setRegion(source.getRegion());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        target.setCommune(source.getCommune());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        target.setAddress(source.getAddress());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public static void apply(ProfileRequest request, User account) {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        validate(request);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        account.setRun(request.getRun().toUpperCase(java.util.Locale.ROOT));
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        account.setLastName(request.getLastName().trim());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        account.setBirthDate(request.getBirthDate());
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        account.setRegion(request.getRegion().trim());
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        account.setCommune(request.getCommune().trim());
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        account.setAddress(request.getAddress().trim());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public static Map<String, Object> payload(ProfileRequest profile, String name, String email) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Map<String, Object> data = new HashMap<>();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        data.put("name", name); data.put("email", email);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        data.put("run", profile.getRun());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        data.put("lastName", profile.getLastName());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        data.put("birthDate", profile.getBirthDate());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        data.put("region", profile.getRegion());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        data.put("commune", profile.getCommune());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        data.put("address", profile.getAddress());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return data;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
