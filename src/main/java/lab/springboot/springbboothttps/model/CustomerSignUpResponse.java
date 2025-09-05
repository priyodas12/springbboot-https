package lab.springboot.springbboothttps.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class CustomerSignUpResponse {
    private int signUpStatus;
    private Date signupTime;
}
