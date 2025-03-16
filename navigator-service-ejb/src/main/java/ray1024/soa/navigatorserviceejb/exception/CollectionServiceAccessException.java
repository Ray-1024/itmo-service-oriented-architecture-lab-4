package ray1024.soa.navigatorserviceejb.exception;

import lombok.*;
import ray1024.soa.navigatorserviceejb.model.dto.ErrorDto;

import java.text.SimpleDateFormat;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CollectionServiceAccessException extends RuntimeException {
    private ErrorDto error;

    public CollectionServiceAccessException(String message) {
        error = new ErrorDto(message, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
    }
}
