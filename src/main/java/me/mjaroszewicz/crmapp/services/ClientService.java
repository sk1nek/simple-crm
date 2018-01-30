package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.dto.ClientDto;
import me.mjaroszewicz.crmapp.entities.Client;
import me.mjaroszewicz.crmapp.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service("clientService")
public class ClientService {

    private final static Logger log = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepository;

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientByName(String name) {
        return clientRepository.findOneByName(name);
    }

    public Client getClient(Long id){
        return clientRepository.findOne(id);
    }

    public void addNewClient(ClientDto clientDto) {

        Client client = new Client(
                clientDto.getName(),
                clientDto.getDescription(),
                clientDto.getAddress(),
                System.currentTimeMillis());

        if (clientDto.getPhone() != null)
            client.setPhone(clientDto.getPhone());

        if (clientDto.getEmail() != null)
            client.setEmail(clientDto.getEmail());

        clientRepository.save(client);
        log.debug("Saved new client: " + client);
    }

}
