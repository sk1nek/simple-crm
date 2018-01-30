package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.dto.UserRegistrationDto;
import me.mjaroszewicz.crmapp.entities.User;
import me.mjaroszewicz.crmapp.exceptions.RegistrationException;
import me.mjaroszewicz.crmapp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private final static String[] DEFAULT_ROLES = {"ROLE_USER"};

    @Autowired
    private UserRepository userRepo;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }


    @Transactional
    public void activateUser(Long id){
        User user = userRepo.findOne(id);
        user.setActive(true);
        userRepo.save(user);
    }

    @Transactional
    public void deactivateUser(Long id){
        User user = userRepo.findOne(id);
        user.setActive(false);
        userRepo.save(user);
    }

    @Transactional
    public void grantAdmin(Long id){
        User user = userRepo.findOne(id);
        Set<String> permissions = user.getPermissions();
        permissions.add("ROLE_ADMIN");
        user.setPermissions(permissions);
        userRepo.save(user);
    }

    @Transactional
    public void revokeAdmin(Long id){
        User user = userRepo.findOne(id);
        Set<String> permissions = user.getPermissions();
        permissions.remove("ROLE_ADMIN");
        user.setPermissions(permissions);
        userRepo.save(user);
    }

    @Transactional
    public User registerNewUser(UserRegistrationDto dto) throws RegistrationException{

        if(userRepo.findOneByEmail(dto.getEmail()) != null)
            throw new RegistrationException("Email already taken");

        if(userRepo.findOneByUsername(dto.getUsername()) != null)
            throw new RegistrationException("Username already taken");

        User user = new User(
                dto.getUsername(),
                passwordEncoder().encode(dto.getPassword()),
                dto.getEmail());

        user.setActive(false);

        Set<String> permissions = new HashSet<>(Arrays.asList(DEFAULT_ROLES));

        if(user.getUsername().equals("admin"))
            permissions.add("ROLE_ADMIN");

        user.setPermissions(permissions);

        log.info("User " + user.getUsername() + " registered");

        return userRepo.save(user);
    }

    @PostConstruct
    private void init() throws RegistrationException{
        User user = registerNewUser(new UserRegistrationDto("admin", "admin", "admin@admin.com"));

    }



}
