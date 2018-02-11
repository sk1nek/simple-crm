package me.mjaroszewicz.crmapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.mjaroszewicz.crmapp.entities.*;
import me.mjaroszewicz.crmapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ObjectMapper objectMapper;

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

    public List<Order> getLastThreeOrders(){
        return orderRepository.findFirst3ByOrderByIdDesc();
    }

    /**@return Returns string representation Json Array containing payments and expenses sums from last 8 weeks.
     * Array needs to be parsed from String before being used.
     * @throws JsonProcessingException
     */
    public ArrayNode getEightWeekFinanceSummary() {

        Long eightWeeksMillis = 1000 * 60 * 60 * 24 * 7 * 8L;
        Long oneWeekMillis = 1000 * 60 * 60 * 24 * 7L;
        Long eightWeeksMillisFromNow = System.currentTimeMillis() - eightWeeksMillis;

        //array to be parsed on return
        Double[][] ret = new Double[2][];
        List<Expense> expenses = expenseRepository.findAllByDateMilisGreaterThan(eightWeeksMillisFromNow);

        Double[] expenseSums = new Double[8];
        for(int i = 0 ; i < 8 ; i++)
            expenseSums[i] = 0.0;


        expenses.stream().forEach(p->{
            Long date = p.getDateMilis();
            int position = (int) ((System.currentTimeMillis() - oneWeekMillis - date) / oneWeekMillis);

            if(position != 0){
                Double sum = expenseSums[position];
                sum += p.getValue();
                expenseSums[position] = sum;
            }
        });

        List<Payment> payments = paymentRepository.findAllByDateMilisGreaterThan(eightWeeksMillisFromNow);

        Double[] paymentSums = new Double[8];
        for(int i = 0; i < 8 ; i++)
            paymentSums[i] = 0.0;

        payments.stream().forEach(p->{
            Long date = p.getDateMilis();
            int position = (int) ((System.currentTimeMillis() - oneWeekMillis - date) / oneWeekMillis);

                if(position != 0){
                    Double sum = paymentSums[position - 1];
                    sum += p.getAmount();
                    paymentSums[position - 1] = sum;
                }
        });

        ret[0] = paymentSums;
        ret[1] = expenseSums;


        ArrayNode o = objectMapper.createArrayNode();
        ArrayNode paymentsNode = o.arrayNode();
        for (Double d : paymentSums)
            paymentsNode.add(d);

        ArrayNode expensesNode = o.arrayNode();
        for(Double d: expenseSums)
            expensesNode.add(d);

        o.add(paymentsNode);
        o.add(expensesNode);


        return o;
    }

    public ArrayNode getNewClientsEightWeeks() {

        Long current = System.currentTimeMillis();

        Long eightWeeksMillis = 1000 * 60 * 60 * 24 * 7 * 8L;
        Long oneWeekMillis = 1000 * 60 * 60 * 24 * 7L;
        Long eightWeeksMillisFromNow = current - eightWeeksMillis;


        List<Client> clients = clientRepository.findAllByDateCreatedMilisGreaterThan(eightWeeksMillisFromNow);

        Integer[] sums = new Integer[8];
        for(int i = 0 ; i < 8 ; i++)
            sums[i] = 0;

        clients.stream().forEach(p->{
            Long date = p.getDateCreatedMilis();
            int position = (int) ((current - oneWeekMillis - date) / oneWeekMillis);

            if(position != 0){
                int sum = sums[position - 1];
                sum++;
                sums[position - 1] = sum;
            }

        });

        ArrayNode ret = objectMapper.createArrayNode();

        for (Integer integer : sums)
            ret.add(integer);

        return ret;
    }



}
