package org.example.lab4web.ejb;


import org.example.lab4web.database.UserBean;
import org.example.lab4web.entity.UserEntity;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.ejb.EJB;

import jakarta.ejb.Stateless;

@Stateless
public class AuthServiceBean {

    @EJB
    private UserBean userBean;

    public boolean register(String username, String password) {
        if (userBean.findByUsername(username).isPresent()) return false;

        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        userBean.save(u);
        return true;
    }

    public boolean validateCredentials(String username, String password) {
        return userBean.findByUsername(username)
                .map(u -> BCrypt.checkpw(password, u.getPasswordHash()))
                .orElse(false);
    }
}
