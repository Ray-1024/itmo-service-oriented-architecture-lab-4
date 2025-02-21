package ray1024.soa.navigatorservice.exception;

import lombok.*;
import ray1024.soa.navigatorservice.model.dto.ErrorDto;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CollectionServiceAccessException extends RuntimeException {
    private ErrorDto error;
}
