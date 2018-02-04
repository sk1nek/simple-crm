package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.dto.OrderDto;
import me.mjaroszewicz.crmapp.entities.Client;
import me.mjaroszewicz.crmapp.entities.Order;
import me.mjaroszewicz.crmapp.repositories.ClientRepository;
import me.mjaroszewicz.crmapp.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("orderService")
@DependsOn("clientService")
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    public void addNewOrder(OrderDto orderDto){

        LocalDateTime ldt = LocalDateTime.now();
        String currentDate = ldt.format(DateTimeFormatter.ISO_DATE);

        addNewOrder(-1,
                orderDto.getOrderName(),
                orderDto.getDescription(),
                orderDto.getValue(),
                clientRepository.findOne(orderDto.getClientId()), currentDate, orderDto.getDateDeadline());

    }

    public void addNewOrder(int state,
                            String name,
                            String desc,
                            double value,
                            Client recipient,
                            String dateReceived,
                            String dateDeadline){

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

    public void updateOrder(OrderDto dto, Long id){

        Order order = orderRepository.findOne(id);

        order.setName(dto.getOrderName());
        order.setValue(dto.getValue());
        order.setDescription(dto.getDescription());
        order.setDateDeadline(dto.getDateDeadline());

        orderRepository.save(order);
    }


    public Order getOrder(Long id){
        return orderRepository.findOne(id);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByClient(Long client){
        return getOrdersByClient(clientRepository.findOne(client));
    }

    public List<Order> getOrdersByClient(Client client){

        return orderRepository.findAllByRecipient(client);

    }

}
