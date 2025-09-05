package lab.springboot.springbboothttps.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerSignUpRequest {

    @NotNull(message = "username cannot be empty")
    private String username;
    @NotNull(message = "password cannot be empty")
    private String password;
    @NotNull(message = "email cannot be null")
    @NotEmpty
    private String email;
    private String firstName;
    private String lastName;
    private String address;
}
