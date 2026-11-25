package com.library.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.crypto.SecretKey;

@Component
public class JwtWebMvcFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Override
    public ServerResponse filter(ServerRequest request, HandlerFunction<ServerResponse> next) throws Exception {

        String path = request.path();
        HttpMethod method = request.method();

        // Preflight CORS: el navegador lo envía sin token
        if (method == HttpMethod.OPTIONS) {
            return next.handle(request);
        }

        if (path.startsWith("/auth/login") || path.startsWith("/auth/register")) {
            return next.handle(request);
        }

        // Contenido público: leer el blog y enviar el formulario de contacto
        if (isPublicContent(path, method)) {
            return next.handle(request);
        }

        String authHeader = request.headers().firstHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);

        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);

            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role", String.class);

            if (path.startsWith("/books")
                    && (method == HttpMethod.POST
                    || method == HttpMethod.PUT
                    || method == HttpMethod.DELETE)
                    && !"ADMIN".equals(role)) {
                return ServerResponse.status(HttpStatus.FORBIDDEN).build();
            }

            if (path.startsWith("/auth/users")
                    && method == HttpMethod.DELETE
                    && !"ADMIN".equals(role)) {
                return ServerResponse.status(HttpStatus.FORBIDDEN).build();
            }

            // Todo lo de contenido que no es público (crear/editar/borrar posts, ver mensajes) es solo ADMIN
            if ((path.startsWith("/v1/posts") || path.startsWith("/v1/contact"))
                    && !"ADMIN".equals(role)) {
                return ServerResponse.status(HttpStatus.FORBIDDEN).build();
            }

            return next.handle(request);

        } catch (Exception e) {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private boolean isPublicContent(String path, HttpMethod method) {
        boolean readPosts = method == HttpMethod.GET
                && (path.equals("/v1/posts") || path.startsWith("/v1/posts/"));
        boolean sendContact = method == HttpMethod.POST && path.equals("/v1/contact");
        return readPosts || sendContact;
    }
}