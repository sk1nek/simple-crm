package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.dto.ClientDto;
import me.mjaroszewicz.crmapp.entities.Client;
import me.mjaroszewicz.crmapp.services.ClientService;
import me.mjaroszewicz.crmapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public ModelAndView addNewClient(@Valid @ModelAttribute ClientDto clientDto, ModelAndView mv, Errors err){

        if(err.hasErrors()){
            mv.addObject("errors",
                    err
                            .getAllErrors()
                            .stream()
                            .flatMap(p -> Stream.of(p.getDefaultMessage()))
                            .distinct()
                            .collect(Collectors.toList()));

            return getClientsListing(mv);
        }

        clientService.addNewClient(clientDto);

        mv.addObject("messages", Collections.singletonList("Success!"));

        return getClientsListing(mv);
    }

    @GetMapping("/{id}")
    public ModelAndView getClientDetails(ModelAndView mv, @PathVariable(name = "id") Long id){

        mv.setViewName("clientdetails");

        mv.addObject("orders", orderService.getOrdersByClient(id));
        mv.addObject("client", clientService.getClient(id));

        return mv;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditClientForm(ModelAndView mv, @PathVariable("id") Long id) {

        mv.setViewName("editclient");

        Client client = clientService.getClient(id);
        mv.addObject("client", client.getDto());

        return mv;
    }

    @PostMapping("/edit")
    public ModelAndView handleClientEdit(ModelAndView mv, @Valid ClientDto dto,  Errors errors) {

        long id = dto.getId();

        if(errors.hasErrors()){

            mv.addObject("errors", errors
                    .getAllErrors()
                    .stream()
                    .map(ObjectError::toString)
                    .collect(Collectors.toList()));

            return getEditClientForm(mv, id);
        }

        clientService.updateClient(dto, id);

        mv.addObject("messages", Collections.singletonList("Success!"));

        return getClientsListing(mv);
    }
}
