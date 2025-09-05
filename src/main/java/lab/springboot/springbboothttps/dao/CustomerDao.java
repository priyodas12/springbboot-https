package lab.springboot.springbboothttps.dao;

import lab.springboot.springbboothttps.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {

    Customer findByUsername(String username);
}
