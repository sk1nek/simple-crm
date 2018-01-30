package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/users/action")
    public ModelAndView handleActionRequest(@RequestParam Long target,
                                            @RequestParam String action,
                                            @RequestParam String value,
                                            ModelAndView mv
    ) {


        return getUserManagementListing(mv);
    }

}
