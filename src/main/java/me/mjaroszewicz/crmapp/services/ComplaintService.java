package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.entities.Complaint;
import me.mjaroszewicz.crmapp.entities.Order;
import me.mjaroszewicz.crmapp.repositories.ComplaintRepository;
import me.mjaroszewicz.crmapp.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Service("complaintService")
@DependsOn("orderService")
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Complaint> getAllComplaints(){
        return complaintRepository.findAll();
    }

    @PostConstruct
    private void init(){
        List<Order> orders = orderRepository.findAll();
        complaintRepository.save(new Complaint(1L, orders.get(0), Collections.emptyList(), "2018-01-01", "2018-01-22", "Dupa kamieni kupa", 0, true, "Foo"));
    }


}
