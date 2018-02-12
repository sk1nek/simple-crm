package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.dto.ExpenseDto;
import me.mjaroszewicz.crmapp.dto.PaymentDto;
import me.mjaroszewicz.crmapp.entities.Expense;
import me.mjaroszewicz.crmapp.entities.Order;
import me.mjaroszewicz.crmapp.entities.Payment;
import me.mjaroszewicz.crmapp.exceptions.PersistenceException;
import me.mjaroszewicz.crmapp.repositories.ClientRepository;
import me.mjaroszewicz.crmapp.repositories.ExpenseRepository;
import me.mjaroszewicz.crmapp.repositories.OrderRepository;
import me.mjaroszewicz.crmapp.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("financeService")
public class FinanceService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void addPayment(PaymentDto dto) throws PersistenceException{

        Payment p = new Payment();
        p.setAmount(dto.getValue());
        p.setDateMilis(System.currentTimeMillis());
        p.setDescription(dto.getDescription());
        p.setParentOrderId(dto.getParentOrderId());

        addPaymentToOrder(dto.getParentOrderId(), p);

    }

    public void addPaymentToOrder(Long orderId, Payment payment) throws PersistenceException {

        Order order = orderRepository.findOne(orderId);

        if (order == null)
            throw new PersistenceException("User with id '" + orderId + "' not found'");

        addPaymentToOrder(order, payment);
    }

    public void addPaymentToOrder(Order order, Payment payment) {

        List<Payment> payments = order.getPayments();
        payments.add(payment);

        //calculate payment percentage
        double sum = 0;
        for(Payment p: payments)
            sum += p.getAmount();
        order.setPaymentPercentage((sum / order.getValue()) * 10.0);
        //

        payment.setParentOrderId(order.getId());
        order.setPayments(payments);


        orderRepository.save(order);
//        paymentRepository.save(payment);

    }

    public List<Payment> getAllPayments() {

        return paymentRepository.findAll();
    }

    public List<Expense> getAllExpenses(){

        return expenseRepository.findAll();
    }

    public void addNewExpense(ExpenseDto dto){

        Expense expense = new Expense();
        expense.setDateMilis(System.currentTimeMillis());
        expense.setValue(dto.getValue());
        expense.setDescription(dto.getDescription());

        expenseRepository.save(expense);
    }

    public void removePayment(Long id) throws NullPointerException{

        Payment payment = paymentRepository.findOne(id);
        Order order = orderRepository.findOne(payment.getParentOrderId());

        order.getPayments().remove(payment);
        paymentRepository.delete(id);
    }

    public void removeExpense(Long id){

        expenseRepository.delete(id);

    }



}
