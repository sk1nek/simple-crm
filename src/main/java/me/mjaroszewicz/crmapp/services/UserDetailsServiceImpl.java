package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.entities.User;
import me.mjaroszewicz.crmapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findOneByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Username not found");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        user.getPermissions().forEach(e -> grantedAuthorities.add(new SimpleGrantedAuthority(e)));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                true,
                true,
                true,
                grantedAuthorities
        );

    }


}
