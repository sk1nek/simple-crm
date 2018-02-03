package me.mjaroszewicz.crmapp.repositories;

import me.mjaroszewicz.crmapp.entities.Complaint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends CrudRepository<Complaint, Long> {

    List<Complaint> findAll();

    List<Complaint> findFirst3ByOrderByDateDeadline();


}
