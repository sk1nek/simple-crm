package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping("/logout")
    public String handleLogout(){

        securityService.logoutCurrent();

        return "redirect:/login?logout";
    }
}
