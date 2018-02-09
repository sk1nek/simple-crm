package me.mjaroszewicz.crmapp.repositories;

import me.mjaroszewicz.crmapp.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findAll();

    Client findOneByName(String name);

    List<Client> findAllByDateCreatedMilisGreaterThan(Long date);
}
