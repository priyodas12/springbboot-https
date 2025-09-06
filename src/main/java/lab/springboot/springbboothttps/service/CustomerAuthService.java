package lab.springboot.springbboothttps.service;


import jakarta.validation.Valid;
import lab.springboot.springbboothttps.dao.CustomerDao;
import lab.springboot.springbboothttps.model.Customer;
import lab.springboot.springbboothttps.model.CustomerSignUpRequest;
import lab.springboot.springbboothttps.model.CustomerSignUpResponse;
import lab.springboot.springbboothttps.util.CustomerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerAuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerDao customerDao;
    private final CustomerUtil customerUtil;
    private final CustomerUsernameSuggestionService suggestionService;

    public CustomerAuthService(BCryptPasswordEncoder bCryptPasswordEncoder, CustomerDao customerDao, CustomerUtil customerUtil, CustomerUsernameSuggestionService suggestionService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customerDao = customerDao;
        this.customerUtil = customerUtil;
        this.suggestionService = suggestionService;
    }

    @Transactional
    public CustomerSignUpResponse signUpCustomer(@Valid CustomerSignUpRequest customerSignUpRequest) {
        Customer customer = customerUtil.parseCustomer(customerSignUpRequest, bCryptPasswordEncoder);
        Optional<Customer> customerOptional = Optional.of(customerDao.save(customer));
        return customerOptional.map(res -> CustomerSignUpResponse.builder()
                .signUpStatus(201)
                .build()).orElse(CustomerSignUpResponse.builder()
                .signUpStatus(500)
                .build());
    }

    @Transactional
    public boolean createBulkCustomer(int count) {
        if (count > 100) {
            return false;
        } else {
            try {
                List<Customer> customers = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Customer customer = customerUtil.createRandomCustomer(suggestionService, bCryptPasswordEncoder);
                    Optional<Customer> doesExist = customerDao.findCustomerByUsernameLowerCase(customer.getUsername());
                    if (doesExist.isPresent()) {
                        log.error("Customer with name {} already exists", customer.getUsername());
                    } else {
                        customers.add(customer);
                    }
                }
                customerDao.saveAll(customers);
            } catch (Exception e) {
                log.error("***********Exception: {} *********** {}", e.getMessage(), e.getCause().toString());
                throw new RuntimeException("Exception while saving customers", e);
            } finally {
                log.info("Customers bulk save function completed!");
            }
        }
        log.info("Created Customer with count {}", count);
        return true;
    }


}
