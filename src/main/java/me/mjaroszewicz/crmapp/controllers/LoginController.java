package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.dto.LoginDto;
import me.mjaroszewicz.crmapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.servlet.ModelAndView;

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
    public String handleLogin(LoginDto user) {

        securityService.autologin(user.getUsername(), user.getPassword());

        return "redirect:/dashboard";
    }

}
