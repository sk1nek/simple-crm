package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.dto.OrderDto;
import me.mjaroszewicz.crmapp.entities.Client;
import me.mjaroszewicz.crmapp.entities.Order;
import me.mjaroszewicz.crmapp.exceptions.PersistenceException;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ModelAndView getOrdersListing(ModelAndView mv){

        mv.setViewName("orders");
        mv.addObject("orders", orderService.getAllOrders());

        return mv;
    }

    @GetMapping("/new")
    public ModelAndView getNewOrderCreator(ModelAndView mv){
        mv.setViewName("neworder");

        List<Client> clientList = clientService.getAllClients();

        mv.addObject("clients", clientList);
        mv.addObject("orderdto", new OrderDto());


        return mv;
    }

    @PostMapping("/new")
    public ModelAndView createNewOrder(@Valid @ModelAttribute("orderdto") OrderDto orderDto, ModelAndView mv, Errors err){

        if(err.hasErrors()){
            mv.addObject("errors", err.getAllErrors());
            getOrdersListing(mv);
        }

        orderService.addNewOrder(orderDto);

        mv.addObject("messages", Collections.singletonList("Success!"));

        return getOrdersListing(mv);

    }

    @GetMapping("/delete/{id}")
    public ModelAndView handleOrderDeletion(ModelAndView mv, @PathVariable Long id) {

        try{
            orderService.deleteOrder(id);
        }catch(PersistenceException pex){
            mv.addObject("errors", Collections.singletonList(pex.getMessage()));
            return getOrdersListing(mv);
        }

        mv.addObject("messages", Collections.singletonList("Success!"));
        return getOrdersListing(mv);
    }

    @GetMapping("/{id}")
    public ModelAndView getOrderDetails(ModelAndView mv, @PathVariable("id") Long id) {

        mv.setViewName("orderdetails");

        Order order = orderService.getOrder(id);
        mv.addObject("order", order);

        return mv;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditOrderForm(ModelAndView mv, @PathVariable("id") Long id){

        mv.setViewName("editorder");

        Order order = orderService.getOrder(id);

        if(order == null){
            mv.addObject("errors", Collections.singletonList("Invalid id"));
            return getOrdersListing(mv);
        }

        mv.addObject("orderid", id);
        mv.addObject("order", order.getDto());


        return mv;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView handleOrderEditFormSubmit(ModelAndView mv,
                                                  @PathVariable Long id,
                                                  @Valid @RequestBody OrderDto orderDto,
                                                  Errors err) {

        if(err.hasErrors()){
            mv.addObject("errors", err.getAllErrors()
                    .stream()
                    .map(ObjectError::toString)
                    .collect(Collectors.toList()));

            return getOrderDetails(mv, id);
        }

        orderService.updateOrder(orderDto, id);

        return getOrderDetails(mv, id);
    }

}
