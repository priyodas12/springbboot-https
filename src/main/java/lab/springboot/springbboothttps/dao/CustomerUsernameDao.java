package lab.springboot.springbboothttps.dao;

import lab.springboot.springbboothttps.model.CustomerUsername;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerUsernameDao extends JpaRepository<CustomerUsername, String> {


}
