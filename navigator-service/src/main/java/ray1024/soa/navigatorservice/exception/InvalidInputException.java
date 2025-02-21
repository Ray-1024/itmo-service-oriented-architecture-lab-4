package ray1024.soa.navigatorservice.exception;

import lombok.*;
import ray1024.soa.navigatorservice.model.dto.ErrorDto;
import ray1024.soa.navigatorservice.model.dto.InvalidParamDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvalidInputException extends RuntimeException {
    private List<InvalidParamDto> invalidParams;
    private ErrorDto error;
}
