package me.mjaroszewicz.crmapp.repositories;

import me.mjaroszewicz.crmapp.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{

    List<Order> findAll();
}
