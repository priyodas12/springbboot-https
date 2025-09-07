package lab.springboot.springbboothttps.util;

import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lab.springboot.springbboothttps.model.Customer;
import lab.springboot.springbboothttps.model.CustomerSignUpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerUtil {

    public Customer parseCustomer(@Valid CustomerSignUpRequest customerSignUpRequest, BCryptPasswordEncoder bCryptPasswordEncoder) {

        return Customer.builder()
                .username(customerSignUpRequest.getUsername())
                .firstName(customerSignUpRequest.getFirstName())
                .lastName(customerSignUpRequest.getLastName())
                .email(customerSignUpRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(customerSignUpRequest.getPassword()))
                .address(customerSignUpRequest.getAddress())
                .phone(null)
                .createdDate(new Date(System.currentTimeMillis()))
                .updatedDate(new Date(System.currentTimeMillis()))
                .build();
    }

    // todo: clean code here
    public Customer createRandomCustomer(Faker faker, String username, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Customer.builder()
                .username(username.toLowerCase())
                .isActive(true)
                .createdDate(new Date(System.currentTimeMillis()))
                .updatedDate(new Date(System.currentTimeMillis()))
                .phone(faker.phoneNumber().phoneNumber())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(username.toLowerCase() + "@" + faker.internet().domainWord().toLowerCase() + ".com")
                .address(faker.address().fullAddress())
                .password(bCryptPasswordEncoder.encode(username.toLowerCase()))
                .build();
    }

}
