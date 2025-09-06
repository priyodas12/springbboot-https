package lab.springboot.springbboothttps.exception;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ExceptionContext {
    private String message;
    private String errorDetails;
    private int status;
    private String path;
    private Instant timestamp;
}
