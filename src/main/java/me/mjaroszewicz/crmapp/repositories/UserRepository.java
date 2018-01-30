package me.mjaroszewicz.crmapp.repositories;

import me.mjaroszewicz.crmapp.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findOneByEmail(String email);

    User findOneByUsername(String username);

    List<User> findAll();

}
