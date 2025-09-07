package lab.springboot.springbboothttps.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsernameSuggestionRequest {
    @NotNull
    private String characters;
    @NotNull
    private int count;
}
