package ray1024.soa.collectionservice.exception;

import lombok.*;
import ray1024.soa.collectionservice.model.dto.ErrorDto;
import ray1024.soa.collectionservice.model.dto.InvalidParamDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvalidBodyParamException extends RuntimeException {
    private List<InvalidParamDto> invalidParams;
    private ErrorDto error;
}
