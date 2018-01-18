package me.mjaroszewicz.crmapp.repositories;

import me.mjaroszewicz.crmapp.entities.Complaint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends CrudRepository<Complaint, Long> {

}
