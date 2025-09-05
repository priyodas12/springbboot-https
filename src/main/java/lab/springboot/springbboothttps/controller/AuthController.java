package lab.springboot.springbboothttps.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lab.springboot.springbboothttps.model.CustomerSignUpRequest;
import lab.springboot.springbboothttps.model.CustomerSignUpResponse;
import lab.springboot.springbboothttps.service.CustomerAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomerAuthService customerAuthService;

    public AuthController(CustomerAuthService customerAuthService) {
        this.customerAuthService = customerAuthService;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomerSignUpResponse> signUp(@Valid @RequestBody CustomerSignUpRequest customerSignUpRequest) {
        CustomerSignUpResponse customerSignUpResponse = customerAuthService.signUpCustomer(customerSignUpRequest);
        return ResponseEntity.status(customerSignUpResponse.getSignUpStatus()).body(customerSignUpResponse);
    }
}
