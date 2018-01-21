package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.dto.OrderDto;
import me.mjaroszewicz.crmapp.entities.Client;
import me.mjaroszewicz.crmapp.entities.Order;
import me.mjaroszewicz.crmapp.services.ClientService;
import me.mjaroszewicz.crmapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    public ModelAndView createNewOrder(@ModelAttribute OrderDto orderDto, ModelAndView mv, Errors err){

        mv.setViewName("redirect:/orders");

        if(err.hasErrors()){
            mv.addObject("error", err.getAllErrors());
        }

        orderService.addNewOrder(orderDto);

        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView getOrderDetails(ModelAndView mv, @RequestAttribute(name = "id") Long id) {

        mv.setViewName("orderdetails");

        Order order = orderService.getOrder(id);
        mv.addObject("order", order);

        return mv;
    }

}
