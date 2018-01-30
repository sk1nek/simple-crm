package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.annotations.ValidClient;
import me.mjaroszewicz.crmapp.dto.ClientDto;
import me.mjaroszewicz.crmapp.services.ClientService;
import me.mjaroszewicz.crmapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;


    @GetMapping
    public ModelAndView getClientsListing(ModelAndView mv){

        mv.setViewName("clients");

        mv.addObject("clients", clientService.getAllClients());

        return mv;
    }

    @GetMapping("/new")
    public ModelAndView getNewClientForm(ModelAndView mv){

        mv.setViewName("newclient");
        mv.addObject("clientDto", new ClientDto());

        return mv;
    }

    @PostMapping("/new")
    public ModelAndView addNewClient(@ValidClient @ModelAttribute ClientDto clientDto, ModelAndView mv, Errors err){

        if(err.hasErrors()){
            mv.addObject("errors", err.getAllErrors());
            return getClientsListing(mv);
        }

        clientService.addNewClient(clientDto);

        mv.addObject("messages", Collections.singletonList("Success!"));

        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView getClientDetails(ModelAndView mv, @PathVariable(name = "id") Long id){

        mv.setViewName("clientdetails");

        mv.addObject("orders", orderService.getOrdersByClient(id));
        mv.addObject("client", clientService.getClient(id));

        return mv;
    }
}
