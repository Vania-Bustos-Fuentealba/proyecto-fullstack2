// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.security.
package com.library.authservice.security;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.Role;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.User;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.Claims;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.Jwts;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.SignatureAlgorithm;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.io.Decoders;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.security.Keys;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.annotation.PostConstruct;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.beans.factory.annotation.Value;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.security.Key;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Date;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Declara el tipo JwtService y su contrato.
public class JwtService {

    // Nota de lectura: Lee una propiedad de configuración o variable de entorno; no escribe un secreto en el código.
    @Value("${jwt.secret}")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String secretKey;

    // Nota de lectura: Lee una propiedad de configuración o variable de entorno; no escribe un secreto en el código.
    @Value("${jwt.expiration}")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private long jwtExpiration;

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Key signingKey;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @PostConstruct
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void init() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // Nota de lectura: Asigna al objeto la dependencia o el valor recibido por el método.
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String generateToken(User user) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return Jwts.builder()
                // Nota de lectura: Encadena la operación setSubject al resultado anterior.
                .setSubject(user.getEmail())
                // Nota de lectura: Encadena la operación claim al resultado anterior.
                .claim("role", user.getRole().name())
                // Nota de lectura: Encadena la operación claim al resultado anterior.
                .claim("accountId", user.getId())
                // Nota de lectura: Encadena la operación claim al resultado anterior.
                .claim("tokenVersion", user.getTokenVersion())
                // Nota de lectura: Encadena la operación setIssuedAt al resultado anterior.
                .setIssuedAt(new Date())
                // Nota de lectura: Encadena la operación setExpiration al resultado anterior.
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                // Nota de lectura: Encadena la operación signWith al resultado anterior.
                .signWith(signingKey, SignatureAlgorithm.HS256)
                // Nota de lectura: Encadena la operación compact al resultado anterior.
                .compact();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // La identidad no se puede transferir a otra cuenta que reutilice el mismo correo.
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public boolean matchesAccount(String token, User user) {
        // Nota de lectura: Participa en la validación criptográfica del JWT con la clave del servidor.
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Number id = claims.get("accountId", Number.class);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Number version = claims.get("tokenVersion", Number.class);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return id != null && version != null && user.getId() != null
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                && id.longValue() == user.getId() && version.longValue() == user.getTokenVersion();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String extractEmail(String token) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Claims claims = Jwts.parser()
                // Nota de lectura: Participa en la validación criptográfica del JWT con la clave del servidor.
                .setSigningKey(signingKey)
                // Nota de lectura: Participa en la validación criptográfica del JWT con la clave del servidor.
                .parseClaimsJws(token)
                // Nota de lectura: Encadena la operación getBody al resultado anterior.
                .getBody();

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return claims.getSubject();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}