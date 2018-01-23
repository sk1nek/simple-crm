package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.annotations.ValidClient;
import me.mjaroszewicz.crmapp.dto.ClientDto;
import me.mjaroszewicz.crmapp.entities.Client;
import me.mjaroszewicz.crmapp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/new")
    public ModelAndView getNewClientForm(ModelAndView mv, @RequestParam(required = false) boolean hasErrors){

        mv.setViewName("newclient");
        mv.addObject("clientDto", new ClientDto());

        if(hasErrors)
            mv.addObject("hasErrors", hasErrors);

        return mv;
    }

    @PostMapping("/new")
    public ModelAndView addNewClient(@ValidClient @ModelAttribute ClientDto clientDto, ModelAndView mv, Errors err){

        if(err.hasErrors())
            return new ModelAndView("redirect:/clients/new?hasErrors=true");

        clientService.addNewClient(clientDto);

        return mv;
    }
}
