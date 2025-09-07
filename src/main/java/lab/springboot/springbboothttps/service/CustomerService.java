package lab.springboot.springbboothttps.service;


import jakarta.validation.Valid;
import lab.springboot.springbboothttps.dao.CustomerDao;
import lab.springboot.springbboothttps.model.*;
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
public class CustomerService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerDao customerDao;
    private final CustomerUtil customerUtil;
    private final CustomerUsernameService customerUsernameService;


    public CustomerService(BCryptPasswordEncoder bCryptPasswordEncoder, CustomerDao customerDao, CustomerUtil customerUtil, CustomerUsernameService customerUsernameService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customerDao = customerDao;
        this.customerUtil = customerUtil;
        this.customerUsernameService = customerUsernameService;
    }


    @Transactional
    public CustomerSignUpResponse signUpCustomer(@Valid CustomerSignUpRequest customerSignUpRequest) {
        Customer customer = customerUtil.parseCustomer(customerSignUpRequest, bCryptPasswordEncoder);
        Optional<Customer> customerOptional = Optional.of(customerDao.save(customer));

        return customerOptional.map(res -> {
            customerUsernameService.saveUsernames(List.of(res));
            return CustomerSignUpResponse.builder()
                    .signUpStatus(201)
                    .build();
        }).orElse(CustomerSignUpResponse.builder()
                .signUpStatus(500)
                .build());
    }

    public List<String> suggestedUsernames(UsernameSuggestionRequest request) {
        List<String> suggestedUsernames = customerUsernameService.usernameSuggestion(request.getCharacters(), request.getCount());
        List<String> validSuggestedUsernames = new ArrayList<>();
        for (String suggestedUsername : suggestedUsernames) {
            Optional<CustomerUsername> optionalCustomerUsername = customerUsernameService.findUsername(suggestedUsername);
            if (optionalCustomerUsername.isEmpty()) {
                validSuggestedUsernames.add(suggestedUsername);
            }
        }
        return validSuggestedUsernames;
    }

    @Transactional
    public boolean createBulkCustomer(int count) {
        if (count > 100) {
            return false;
        } else {
            try {
                List<Customer> customers = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Customer customer = customerUsernameService.createRandomCustomerForBulkLoad(bCryptPasswordEncoder);
                    Optional<Customer> doesExist = customerDao.findCustomerByUsernameLowerCase(customer.getUsername());
                    if (doesExist.isPresent()) {
                        log.error("Customer with name {} already exists", customer.getUsername());
                    } else {
                        customers.add(customer);
                    }
                }
                customerDao.saveAll(customers);
                customerUsernameService.saveUsernames(customers);
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


    public List<String> searchUsernames(String term) {
        log.info("searching results for {}", term);
        List<String> fuzzyUsernames = customerDao.findFuzzyUsernames(term, 10);
        log.info("searching results for {}", fuzzyUsernames);
        return fuzzyUsernames;
    }
}
