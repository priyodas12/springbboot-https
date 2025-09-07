package lab.springboot.springbboothttps.dao;

import lab.springboot.springbboothttps.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {

    Customer findByUsername(String username);

    @Query("SELECT c FROM Customer c where lower(c.username)=lower(?1) ")
    Optional<Customer> findCustomerByUsernameLowerCase(String username);

    @Query(value = "SELECT username FROM customers WHERE username LIKE :prefix || '%' ORDER BY username LIMIT :limit", nativeQuery = true)
    List<String> findPrefixUsernames(@Param("prefix") String prefix, @Param("limit") int limit);

    @Query(value = """
            SELECT username
            FROM customers
            WHERE similarity(username, :term) > 0
            ORDER BY similarity(username, :term) DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<String> findFuzzyUsernames(@Param("term") String term, @Param("limit") int limit);
}
