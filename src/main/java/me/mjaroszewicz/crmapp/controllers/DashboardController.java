package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.services.DataAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DataAggregationService dataAggregationService;

    @GetMapping
    public ModelAndView getDashboard(ModelAndView mv){

        mv.setViewName("dashboard");

        mv.addObject("clientsCount", dataAggregationService.getClientsCount());
        mv.addObject("activeOrdersCount", dataAggregationService.getActiveOrdersCount());
        mv.addObject("completedOrdersCount", dataAggregationService.getCompletedOrdersCount());
        mv.addObject("unresolvedComplaintsCount", dataAggregationService.getUnresolvedComplaintsCount());
        mv.addObject("ordersValueSum", dataAggregationService.getOrdersValueSum());

        mv.addObject("payments", dataAggregationService.getLastThreePayments());
        mv.addObject("complaints", dataAggregationService.getLastThreeComplaints());
        mv.addObject("orders", dataAggregationService.getLastThreeOrders());

        return mv;
    }


}
