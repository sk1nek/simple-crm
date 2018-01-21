package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.annotations.ValidUserRegistration;
import me.mjaroszewicz.crmapp.dto.UserRegistrationDto;
import me.mjaroszewicz.crmapp.exceptions.RegistrationException;
import me.mjaroszewicz.crmapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public ModelAndView getRegistrationForm(ModelAndView mv){

        mv.setViewName("register");

        mv.addObject("user", new UserRegistrationDto());

        return mv;
    }

    @PostMapping("/register")
    public ModelAndView handleRegister(ModelAndView mv, @ValidUserRegistration UserRegistrationDto user, Errors err){

        mv.setViewName("register");
        List<String> errors = new ArrayList<>();

        if(err.hasErrors()){
            err.getAllErrors().forEach(e -> errors.add(e.getDefaultMessage()));
            mv.addObject("errors", errors);
            return mv;
        }

        try{
            userService.registerNewUser(user);
        }catch (RegistrationException rex){
            mv.setViewName("/register");
            errors.add(rex.getMessage());
            mv.addObject("errors", errors);
            return mv;
        }

        mv.setViewName("redirect:/dashboard");
        return mv;
    }

}