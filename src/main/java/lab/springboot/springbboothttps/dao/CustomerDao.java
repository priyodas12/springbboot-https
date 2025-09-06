package lab.springboot.springbboothttps.dao;

import lab.springboot.springbboothttps.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {

    Customer findByUsername(String username);

    @Query("SELECT c FROM Customer c where lower(c.username)=lower(?1) ")
    Optional<Customer> findCustomerByUsernameLowerCase(String username);
}
