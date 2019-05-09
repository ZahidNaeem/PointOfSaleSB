package org.zahid.apps.web.pos.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zahid.apps.web.pos.entity.UserDetail;
import org.zahid.apps.web.pos.repo.UserDetailRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    private static final Logger LOG = LogManager.getLogger(MyUserDetailsService.class);
    @Autowired
    UserDetailRepo userDetailRepo;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<UserDetail> userDetails = userDetailRepo.findByUName(s);
        if (userDetails != null && userDetails.size() > 0) {
            UserDetail detail = userDetails.get(0);
            if (detail != null) {
                LOG.log(Level.INFO,"Password: {0}", passwordEncoder(detail.getUPassword()));
                User user = new User(detail.getUName(), passwordEncoder(detail.getUPassword()), true, true, true, true, buildUserAuthority(s));
                return user;
            }

        }

        return null;
    }


    private List<GrantedAuthority> buildUserAuthority(String s) {

        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();

        // add user's authorities
        for (UserDetail detail : userDetailRepo.findByUName(s)) {
            authSet.add(new SimpleGrantedAuthority(detail.getGName()));
        }
        List<GrantedAuthority> Result = new ArrayList<>(authSet);
        return Result;
    }

    private String passwordEncoder(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
