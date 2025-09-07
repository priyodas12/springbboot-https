package lab.springboot.springbboothttps.controller;


import jakarta.validation.Valid;
import lab.springboot.springbboothttps.model.CustomerSignUpRequest;
import lab.springboot.springbboothttps.model.CustomerSignUpResponse;
import lab.springboot.springbboothttps.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomerSignUpResponse> signUp(@Valid @RequestBody CustomerSignUpRequest customerSignUpRequest) {
        CustomerSignUpResponse customerSignUpResponse = customerService.signUpCustomer(customerSignUpRequest);
        return ResponseEntity.status(customerSignUpResponse.getSignUpStatus()).body(customerSignUpResponse);
    }
}
