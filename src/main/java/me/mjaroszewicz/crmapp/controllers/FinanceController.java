package me.mjaroszewicz.crmapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import me.mjaroszewicz.crmapp.services.DataAggregationService;
import me.mjaroszewicz.crmapp.services.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@RequestMapping("/finance")
@Controller
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @Autowired
    private DataAggregationService dataAggregationService;

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @GetMapping()
    public ModelAndView getFinancesDashboard(ModelAndView mv) throws JsonProcessingException {

        mv.setViewName("finances");

        ArrayNode an = dataAggregationService.getEightWeekFinanceSummary();
        mv.addObject("payments", an.get(0).toString());
        mv.addObject("expenses", an.get(1).toString());

        mv.addObject("clients", dataAggregationService.getNewClientsEightWeeks().toString());

        return mv;
    }

    @GetMapping("/payments")
    public ModelAndView getPaymentsListing(ModelAndView mv) {

        mv.setViewName("payments");

        mv.addObject("payments", financeService.getAllPayments());

        return mv;
    }

    @GetMapping("/payments/delete/{id}")
    public ModelAndView deletePayment(ModelAndView mv, @PathVariable Long id){

        financeService.removePayment(id);

        mv.addObject("messages", Collections.singletonList("Success!"));

        return getPaymentsListing(mv);
    }

    @GetMapping("/expenses")
    public ModelAndView getExpensesListing(ModelAndView mv) {

        mv.setViewName("expenses");

        mv.addObject("expenses", financeService.getAllExpenses());

        return mv;
    }

    @GetMapping("/expenses/delete/{id}")
    public ModelAndView removeExpense(ModelAndView mv, @PathVariable Long id){

        financeService.removeExpense(id);

        mv.addObject("messages", Collections.singletonList("Success!"));

        return getExpensesListing(mv);

    }
}
