package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.services.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/finance")
@Controller
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @GetMapping()
    public ModelAndView getFinancesDashboard(ModelAndView mv) {

        mv.setViewName("finances");


        return mv;
    }

    @GetMapping("/payments")
    public ModelAndView getPaymentsListing(ModelAndView mv) {

        mv.setViewName("payments");

        mv.addObject("payments", financeService.getAllPayments());

        return mv;
    }

    @GetMapping("/expenses")
    public ModelAndView getExpensesListing(ModelAndView mv){

        mv.setViewName("expenses");


        return mv;
    }
}
