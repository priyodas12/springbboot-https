package lab.springboot.springbboothttps.util;

import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lab.springboot.springbboothttps.model.Customer;
import lab.springboot.springbboothttps.model.CustomerSignUpRequest;
import lab.springboot.springbboothttps.service.CustomerUsernameSuggestionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.random.RandomGenerator;

@Log4j2
@Service
public class CustomerUtil {

    private final Faker faker;

    public CustomerUtil(Faker faker) {
        this.faker = faker;
    }

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

    public Customer createRandomCustomer(CustomerUsernameSuggestionService suggestionService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        List<String> randomUsernames = suggestionService.usernameSuggestion(faker.funnyName().name(), 5);
        log.debug("suggested randomUsernames: " + randomUsernames);
        RandomGenerator secureGenerator = new SecureRandom();
        String randomUsername = randomUsernames.get(secureGenerator.nextInt(5));
        log.debug("random username: {}, at {}", randomUsername, System.currentTimeMillis());
        return Customer.builder()
                .username(randomUsername.toLowerCase())
                .isActive(true)
                .createdDate(new Date(System.currentTimeMillis()))
                .updatedDate(new Date(System.currentTimeMillis()))
                .phone(faker.phoneNumber().phoneNumber())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(randomUsername.toLowerCase() + "@" + faker.internet().domainWord().toLowerCase() + ".com")
                .address(faker.address().fullAddress())
                .password(bCryptPasswordEncoder.encode(randomUsername.toLowerCase()))
                .build();
    }
}
