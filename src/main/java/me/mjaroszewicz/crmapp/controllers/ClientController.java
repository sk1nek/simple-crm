package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ModelAndView getClientsListing(ModelAndView mv){

        mv.setViewName("clients");

        mv.addObject("clients", clientService.getAllClients());

        return mv;
    }
}
