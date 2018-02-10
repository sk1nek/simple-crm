package me.mjaroszewicz.crmapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import me.mjaroszewicz.crmapp.dto.PaymentDto;
import me.mjaroszewicz.crmapp.exceptions.PersistenceException;
import me.mjaroszewicz.crmapp.services.DataAggregationService;
import me.mjaroszewicz.crmapp.services.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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

    @GetMapping("/payments/new")
    public ModelAndView getNewPaymentForm(ModelAndView mv, @RequestParam(name = "orderId", required = false) Long orderId){

        mv.setViewName("newpayment");

        PaymentDto paymentDto = new PaymentDto();

        if(orderId != null)
            paymentDto.setParentOrderId(orderId);


        mv.addObject("payment", paymentDto);

        return mv;
    }

    @PostMapping("/payments/new")
    public ModelAndView addNewPayment(ModelAndView mv, @Valid PaymentDto dto, Errors err) {

        if(err.hasErrors()){
            mv.addObject("errors", Collections.singletonList("Invalid payment data. Try again"));
            return getPaymentsListing(mv);
        }

        try {
            financeService.addPayment(dto);
        } catch (PersistenceException e) {
            mv.addObject("errors", Collections.singletonList(e.getMessage()));
            return getPaymentsListing(mv);
        }

        mv.addObject("messages", Collections.singletonList("Success!"));

        return getPaymentsListing(mv);
    }

    @GetMapping("/payments/delete/{id}")
    public ModelAndView deletePayment(ModelAndView mv, @PathVariable Long id){

        try{
            financeService.removePayment(id);
        }catch (NullPointerException ex){
            mv.addObject("errors", Collections.singletonList("Not found"));
            mv.setStatus(HttpStatus.NOT_FOUND);
            return getExpensesListing(mv);
        }

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
