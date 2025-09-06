package lab.springboot.springbboothttps.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(name = "idx_customer_username", columnNames = {"username"})
}, indexes = {
        @Index(name = "idx_customer_username", columnList = "username"),
        @Index(name = "idx_customer_email", columnList = "email_id")
})
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_seq")
    @SequenceGenerator(name = "customers_seq", sequenceName = "customers_id_seq", allocationSize = 1)
    private BigInteger id;
    @Column(name = "username")
    private String username;
    @Column(name = "email_id")
    private String email;
    @Column(name = "is_active")
    private boolean isActive;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedDate;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public Customer() {

    }
}
