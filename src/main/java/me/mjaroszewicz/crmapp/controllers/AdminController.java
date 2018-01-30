package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView getAdminDashboard(ModelAndView mv){

        mv.setViewName("admin");

        return mv;
    }

    @GetMapping("/users")
    public ModelAndView getUserManagementListing(ModelAndView mv){

        mv.setViewName("admin-users");

        mv.addObject("users", userService.getAllUsers());

        return mv;
    }


    @GetMapping("/users/activate/{id}")
    public ModelAndView handleActivateUser(ModelAndView mv, @PathVariable Long id){

        userService.activateUser(id);

        mv.addObject("messages", Collections.singletonList("Success"));

        return getUserManagementListing(mv);
    }

    @GetMapping("/users/deactivate/{id}")
    public ModelAndView handleDeactivateUser(ModelAndView mv, @PathVariable Long id){

        userService.deactivateUser(id);

        mv.addObject("messages", Collections.singletonList("Success"));

        return getUserManagementListing(mv);
    }

    @GetMapping("/users/grantadmin/{id}")
    public ModelAndView handleGrantAdmin(ModelAndView mv, @PathVariable Long id){

        userService.grantAdmin(id);

        mv.addObject("messages", Collections.singletonList("Success"));

        return getUserManagementListing(mv);
    }

    @GetMapping("/users/revokeadmin/{id}")
    public ModelAndView handleRevokeAdmin(ModelAndView mv, @PathVariable Long id){

        userService.revokeAdmin(id);

        mv.addObject("messages", Collections.singletonList("Success"));

        return getUserManagementListing(mv);
    }



}
