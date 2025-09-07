package lab.springboot.springbboothttps.controller;

import lab.springboot.springbboothttps.model.UsernameSuggestionRequest;
import lab.springboot.springbboothttps.service.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// todo: mapping will be changed later after security set up
@Log4j2
@RequestMapping("/auth/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/bulk/{count}")
    public ResponseEntity<Boolean> createBulkCustomer(@PathVariable("count") int count) {
        return ResponseEntity.ok(customerService.createBulkCustomer(count));
    }

    @PostMapping("/suggest/usernames")
    public ResponseEntity<List<String>> suggestedUsernames(@RequestBody UsernameSuggestionRequest request) {
        return ResponseEntity.ok(customerService.suggestedUsernames(request));
    }

    @PostMapping("/fuzzy/{searchUsername}")
    public ResponseEntity<List<String>> suggestedUsernames(@PathVariable("searchUsername") String searchUsername) {
        log.info("searching results for {}", searchUsername);
        return ResponseEntity.ok(customerService.searchUsernames(searchUsername));
    }
}
