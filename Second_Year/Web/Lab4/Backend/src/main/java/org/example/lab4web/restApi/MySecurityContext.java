package org.example.lab4web.restApi;

import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;

public class MySecurityContext implements SecurityContext {
    private final String username;

    public MySecurityContext(String username) {
        this.username = username;
    }

    @Override
    public Principal getUserPrincipal() {
        return () -> username;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }

}
