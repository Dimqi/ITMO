package org.example.lab4web.restApi;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.lab4web.ejb.AuthServiceBean;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {
    private static final String SECRET_KEY = "01234567890123456789012345678901";

    @EJB
    private AuthServiceBean authService;

    public static class AuthRequest {
        public String username;
        public String password;
    }

    @POST
    @Path("/login")
    public Response login(AuthRequest request) {
        boolean valid = authService.validateCredentials(request.username, request.password);
        if(valid) {
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            String token = Jwts.builder()
                    .setSubject(request.username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
            return Response.ok("{\"token\":\"" + token + "\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/register")
    public Response register(AuthRequest request) {
        boolean success = authService.register(request.username, request.password);
        if(success) {
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            String token = Jwts.builder()
                    .setSubject(request.username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 360000000))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
            return Response.ok("{\"token\":\"" + token + "\"}").build();
        }
        else return Response.status(Response.Status.CONFLICT).build();
    }
}
