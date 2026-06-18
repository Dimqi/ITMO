package org.example.lab4web.restApi;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Provider
@Priority(Priorities.AUTHENTICATION)

public class JwtFilter implements ContainerRequestFilter {

    private static final String SECRET_KEY = "01234567890123456789012345678901";
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    @Override
    public void filter(ContainerRequestContext request) {
        String path = request.getUriInfo().getPath();
        if (path.contains("auth")) {
            return;
        }

        String auth = request.getHeaderString("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                String token = auth.substring(7);
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                request.setSecurityContext(new MySecurityContext(claims.getSubject()));
            }catch (Exception e){
                request.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } else {
            request.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
