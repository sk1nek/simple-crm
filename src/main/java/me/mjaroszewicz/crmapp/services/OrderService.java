package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.entities.Client;
import me.mjaroszewicz.crmapp.entities.Order;
import me.mjaroszewicz.crmapp.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void addNewOrder(int state,
                            String name,
                            String desc,
                            double value,
                            Client recipient,
                            long dateReceived,
                            long dateDeadline){

        Order order = new Order();

        order.setName(name);
        order.setState(state);
        order.setDescription(desc);
        order.setValue(value);
        order.setRecipient(recipient);
        order.setDateReceived(dateReceived);
        order.setDateDeadline(dateDeadline);

        orderRepository.save(order);
    }

    public Order getOrder(Long id){
        return orderRepository.findOne(id);
    }

}
