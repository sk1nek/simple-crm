package me.mjaroszewicz.crmapp.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @GetMapping("errors")
    public ModelAndView renderErrorPage(HttpServletRequest request){

        ModelAndView mv = new ModelAndView("errorpage");

        return mv;
    }
}
