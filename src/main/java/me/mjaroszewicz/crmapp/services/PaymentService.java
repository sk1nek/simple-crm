package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.entities.Order;
import me.mjaroszewicz.crmapp.entities.Payment;
import me.mjaroszewicz.crmapp.exceptions.PersistenceException;
import me.mjaroszewicz.crmapp.repositories.OrderRepository;
import me.mjaroszewicz.crmapp.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("paymentService")
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

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

        System.out.println(order.toString());

        orderRepository.save(order);
//        paymentRepository.save(payment);

    }

    @PostConstruct
    private void init(){
        Order order = orderRepository.findAll().get(0);

        addPaymentToOrder(order, new Payment(300d, "Lorem", System.currentTimeMillis()));

        order = orderRepository.findAll().get(1);

        addPaymentToOrder(order, new Payment(500d, "Ipsum", System.currentTimeMillis()));

        order = orderRepository.findAll().get(0);
        addPaymentToOrder(order, new Payment(700d, "Foo", System.currentTimeMillis()));
    }


}
