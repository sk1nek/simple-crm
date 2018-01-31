package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.dto.UserRegistrationDto;
import me.mjaroszewicz.crmapp.entities.User;
import me.mjaroszewicz.crmapp.exceptions.RegistrationException;
import me.mjaroszewicz.crmapp.repositories.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private final static String[] DEFAULT_ROLES = {"ROLE_USER"};

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

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

    /**
     *
     * @param id target user id
     * @param request - http request associated with method call
     * @return true if current user has his rights revoked
     */
    @Transactional
    public boolean revokeAdmin(Long id, HttpServletRequest request){
        User user = userRepo.findOne(id);
        Set<String> permissions = user.getPermissions();
        permissions.remove("ROLE_ADMIN");
        user.setPermissions(permissions);
        userRepo.save(user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        if (name.equals(user.getUsername())) {
            try{
                auth.setAuthenticated(false);
                request.logout();
            }catch (Throwable t){
                t.printStackTrace();
            }finally {
                return true;
            }
        }

        return false;
    }

    @Transactional
    public void changeEmail(String email){

        String username = getCurrentUsername();

        User user = userRepo.findOneByUsername(username);

        user.setEmail(email);

        userRepo.save(user);
    }

    @Transactional
    public void changePhoneNumber(String phone){
        String username = getCurrentUsername();

        User user = userRepo.findOneByUsername(username);

        user.setPhone(phone);

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

        user.setPermissions(permissions);

        log.info("User " + user.getUsername() + " registered");

        return userRepo.save(user);
    }

    private String getCurrentUsername(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }




}
