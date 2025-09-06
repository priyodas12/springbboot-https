package lab.springboot.springbboothttps.controller;

import lab.springboot.springbboothttps.service.CustomerAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// todo: mapping will be changed later after security set up
// ("/customers")
@RequestMapping("/auth")
@RestController
public class CustomerController {

    private final CustomerAuthService customerAuthService;

    public CustomerController(CustomerAuthService customerAuthService) {
        this.customerAuthService = customerAuthService;
    }

    @GetMapping("/customers/{count}")
    public ResponseEntity<Boolean> createBulkCustomer(@PathVariable("count") int count) {
        return ResponseEntity.ok(customerAuthService.createBulkCustomer(count));
    }

    //todo: expose endpoint
    // GET: /customers/suggest/usernames/{count}
    // POST: /customers/fuzzy/usernames/{search-user-name}

}
