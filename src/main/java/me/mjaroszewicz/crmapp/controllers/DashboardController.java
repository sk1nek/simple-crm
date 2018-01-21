package me.mjaroszewicz.crmapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public ModelAndView getDashboard(ModelAndView mv){

        mv.setViewName("dashboard");


        return mv;
    }


}
