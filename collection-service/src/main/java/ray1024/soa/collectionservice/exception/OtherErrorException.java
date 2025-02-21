package ray1024.soa.collectionservice.exception;

import lombok.*;
import ray1024.soa.collectionservice.model.dto.ErrorDto;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OtherErrorException extends RuntimeException {
    private ErrorDto error;
}
