package me.mjaroszewicz.crmapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("")
    public String redirectIndex(){
        return "redirect:/dashboard";
    }

}
