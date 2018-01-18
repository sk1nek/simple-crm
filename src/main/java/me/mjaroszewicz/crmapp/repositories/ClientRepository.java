package me.mjaroszewicz.crmapp.repositories;

import me.mjaroszewicz.crmapp.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long>{
}
