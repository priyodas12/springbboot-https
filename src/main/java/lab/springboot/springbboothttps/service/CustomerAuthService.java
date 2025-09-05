package lab.springboot.springbboothttps.service;

import jakarta.validation.Valid;
import lab.springboot.springbboothttps.dao.CustomerDao;
import lab.springboot.springbboothttps.model.Customer;
import lab.springboot.springbboothttps.model.CustomerSignUpRequest;
import lab.springboot.springbboothttps.model.CustomerSignUpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CustomerAuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerDao customerDao;

    public CustomerAuthService(BCryptPasswordEncoder bCryptPasswordEncoder, CustomerDao customerDao) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customerDao = customerDao;
    }

    public CustomerSignUpResponse signUpCustomer(@Valid CustomerSignUpRequest customerSignUpRequest) {
        Customer customer = parseCustomer(customerSignUpRequest);
        Optional<Customer> customerOptional = Optional.of(customerDao.save(customer));
        return customerOptional.map(res -> CustomerSignUpResponse.builder()
                .signUpStatus(201)
                .build()).orElse(CustomerSignUpResponse.builder()
                .signUpStatus(500)
                .build());
    }

    private Customer parseCustomer(@Valid CustomerSignUpRequest customerSignUpRequest) {

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
}
