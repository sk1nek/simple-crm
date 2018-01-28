package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.dto.ComplaintDto;
import me.mjaroszewicz.crmapp.entities.Complaint;
import me.mjaroszewicz.crmapp.entities.Order;
import me.mjaroszewicz.crmapp.exceptions.ComplaintSubmitException;
import me.mjaroszewicz.crmapp.repositories.ComplaintRepository;
import me.mjaroszewicz.crmapp.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service("complaintService")
@DependsOn("orderService")
public class ComplaintService {

    private static final Logger log = LoggerFactory.getLogger(ComplaintService.class);

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Complaint> getAllComplaints(){
        return complaintRepository.findAll();
    }

    public void registerNewComplaint(ComplaintDto dto) throws ComplaintSubmitException {

        Complaint complaint = new Complaint();

        Order order = orderRepository.findOne(dto.getOrderId());

        if(order == null)
            throw new ComplaintSubmitException("Invalid Order Id");

        complaint.setOrder(order);

        List<MultipartFile> files = (dto.getFiles() != null ? dto.getFiles() : Collections.emptyList());

        complaint.setAttachedFiles(
                files
                        .stream()
                        .distinct()
                        .map(MultipartFile::getOriginalFilename)
                        .collect(Collectors.toList()));

        complaint.setUrgent(dto.isUrgent());
        complaint.setDateDeadline(dto.getDeadline());
        complaint.setState(-1);
        complaint.setDescription(dto.getDescription());
        complaint.setDateCreated(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));

        complaintRepository.save(complaint);
        log.info("Complaint registered: " + complaint.toString());
    }



    @PostConstruct
    private void init(){
        List<Order> orders = orderRepository.findAll();
        complaintRepository.save(new Complaint(1L, orders.get(0), Collections.emptyList(), "2018-01-01", "2018-01-22", "Dupa kamieni kupa", 0, true, "Foo"));
    }



}
