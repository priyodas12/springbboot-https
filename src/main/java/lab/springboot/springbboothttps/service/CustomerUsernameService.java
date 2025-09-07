package lab.springboot.springbboothttps.service;

import com.github.javafaker.Faker;
import lab.springboot.springbboothttps.dao.CustomerUsernameDao;
import lab.springboot.springbboothttps.model.Customer;
import lab.springboot.springbboothttps.model.CustomerUsername;
import lab.springboot.springbboothttps.util.CustomerUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

@Log4j2
@Service
public class CustomerUsernameService {

    // todo: will replace with redis later on
    private static final List<String> prefixAdjectives = List.of("alpha", "blog", "travel", "super", "max", "prime", "pro", "genes", "in", "cn", "sl");
    private final CustomerUsernameDao customerUsernameDao;
    private final CustomerUtil customerUtil;
    private final Faker faker;

    public CustomerUsernameService(CustomerUsernameDao customerUsernameDao, CustomerUtil customerUtil, Faker faker) {
        this.customerUsernameDao = customerUsernameDao;
        this.customerUtil = customerUtil;
        this.faker = faker;
    }

    public Optional<CustomerUsername> findUsername(String username) {
        return customerUsernameDao.findById(username);
    }

    public List<String> usernameSuggestion(String baseUsername, int count) {
        return IntStream.range(0, count).mapToObj(i -> {
            String username = baseUsername.replaceAll("\\s", "").toLowerCase();
            String randomNumber = String.valueOf(RandomGenerator.getDefault().nextInt(1, 9999));
            String randomAdjective = (prefixAdjectives.get(RandomGenerator.getDefault().nextInt(0, prefixAdjectives.size())));
            return username.length() > 7 ? username.substring(0, 7) + randomAdjective + randomNumber : username + randomAdjective + randomNumber;
        }).toList();
    }

    public Customer createRandomCustomerForBulkLoad(BCryptPasswordEncoder bCryptPasswordEncoder) {
        List<String> randomUsernames = usernameSuggestion(faker.funnyName().name(), 5);
        log.debug("suggested randomUsernames: " + randomUsernames);
        String selectedUsername = null;
        for (String randomUsername : randomUsernames) {
            Optional<CustomerUsername> usernameOptional = customerUsernameDao.findById(randomUsername);
            if (usernameOptional.isEmpty()) {
                selectedUsername = randomUsername;
                break;
            }
        }
        log.debug("random username: {}, at {}", selectedUsername, System.currentTimeMillis());
        return customerUtil.createRandomCustomer(faker, selectedUsername, bCryptPasswordEncoder);
    }

    public void saveUsernames(List<Customer> customers) {
        List<CustomerUsername> usernames = customers.stream().map(customer -> CustomerUsername.builder()
                .username(customer.getUsername())
                .customerId(customer.getId())
                .isActive(customer.isActive())
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .build()).toList();
        customerUsernameDao.saveAll(usernames);
    }

}
