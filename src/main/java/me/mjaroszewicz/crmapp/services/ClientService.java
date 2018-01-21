package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.entities.Client;
import me.mjaroszewicz.crmapp.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public Client getClientByName(String name){
        return clientRepository.findOneByName(name);
    }



    @PostConstruct
    private void init(){
        clientRepository.save(new Client(1L, "Thomas", "Ipsum", "Ipsum street 23", "589671453", "thomas@google.com"));
        clientRepository.save(new Client(2L, "Martin", "Lorem", "Ipsum street 73", "593012583", "martin@martin.com"));


        clientRepository.findAll().forEach(e -> System.out.println(e.toString()));

    }
}
