package me.mjaroszewicz.crmapp.validators;

import me.mjaroszewicz.crmapp.entities.User;
import me.mjaroszewicz.crmapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityAnalyzer {

    private final UserRepository userRepository;

    @Autowired
    public SecurityAnalyzer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isLocked(String username){

        User user = userRepository.findOneByUsername(username);
        return !user.isActive();
    }


}
