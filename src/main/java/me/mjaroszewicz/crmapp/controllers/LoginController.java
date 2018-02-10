package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.annotations.ValidLoginDto;
import me.mjaroszewicz.crmapp.dto.LoginDto;
import me.mjaroszewicz.crmapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/login")
    public ModelAndView getLoginPage(ModelAndView mv){

        mv.setViewName("login");

        mv.addObject("user", new LoginDto());

        return mv;
    }

    @PostMapping("/perform-login")
    public ModelAndView handleLogin(ModelAndView mv, @Valid LoginDto user, Errors err) {


        if(err.hasErrors()){
            mv.addObject("errors", Collections.singletonList("Invalid username or password. Please try again with valid credentials"));
            return getLoginPage(mv);
        }

        securityService.autologin(user.getUsername(), user.getPassword());
        mv.setViewName("redirect:/dashboard");

        return mv;
    }

}
