package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.entities.Order;
import me.mjaroszewicz.crmapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/new")
    public ModelAndView getNewOrderCreator(ModelAndView mv){
        mv.setViewName("neworder");

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
