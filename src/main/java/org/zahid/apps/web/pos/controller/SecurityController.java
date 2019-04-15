package org.zahid.apps.web.pos.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Named("securityController")
@Dependent
//@Named("securityController")
//@Dependent
public class SecurityController {

    public String username;

    public String getUsername() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return user;
    }

}
