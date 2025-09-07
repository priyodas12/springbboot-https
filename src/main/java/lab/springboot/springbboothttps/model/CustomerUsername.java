package lab.springboot.springbboothttps.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Builder
@Entity
@Table(name = "customer_usernames")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUsername {
    @Id
    private String username;
    private BigInteger customerId;
    private boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}
