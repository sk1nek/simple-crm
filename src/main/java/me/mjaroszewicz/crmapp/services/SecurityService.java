package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.entities.User;
import me.mjaroszewicz.crmapp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final static Logger log = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;


    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findOneByUsername(auth.getName());
    }

    public void autologin(String username, String password){

        UserDetails userDetails;

        if(userDetailsService.loadUserByUsername(username) != null)
            userDetails = userDetailsService.loadUserByUsername(username);
        else
            return;

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(
                        userDetails, password, userDetails.getAuthorities());

        if(token.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(token);
            log.debug(username + " logged in");
        }

    }

}
