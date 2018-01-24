package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.entities.Complaint;
import me.mjaroszewicz.crmapp.entities.Order;
import me.mjaroszewicz.crmapp.entities.Payment;
import me.mjaroszewicz.crmapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataAggregationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ClientRepository clientRepository;

    public long getClientsCount(){
        return clientRepository.count();
    }

    public long getActiveOrdersCount(){
        long count = 0;
        for(Order o: orderRepository.findAll()){
            if(o.getState() < 1)
                count++;
        }

        return count;
    }

    public long getCompletedOrdersCount(){
        long count = 0;
        for(Order o: orderRepository.findAll()){
            if(o.getState() == 1)
                count++;
        }
        return count;
    }

    public long getUnresolvedComplaintsCount(){
        long count = 0;
        for(Complaint c: complaintRepository.findAll()){
            if(c.getState() != 1)
                count++;
        }

        return count;
    }

    public double getOrdersValueSum(){
        double sum = 0;
        for(Order o: orderRepository.findAll())
            sum += o.getValue();
        return sum;

    }

    public List<Payment> getLastThreePayments(){
        return paymentRepository.findFirst3ByOrderByDateMilisDesc();
    }

    public List<Complaint> getLastThreeComplaints(){
        return complaintRepository.findFirst3ByOrderByDateDeadline();
    }



}
