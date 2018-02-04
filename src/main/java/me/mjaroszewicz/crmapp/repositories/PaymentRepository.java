package me.mjaroszewicz.crmapp.repositories;

import me.mjaroszewicz.crmapp.entities.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    List<Payment> findFirst3ByOrderByDateMilisDesc();

    List<Payment> findAll();


}
