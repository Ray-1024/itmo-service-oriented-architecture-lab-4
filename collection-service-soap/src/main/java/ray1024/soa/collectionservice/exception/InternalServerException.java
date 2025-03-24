package ray1024.soa.collectionservice.exception;

import lombok.*;
import ray1024.soa.collectionservice.model.dto.ErrorDto;

import java.text.SimpleDateFormat;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InternalServerException extends RuntimeException {
    private ErrorDto error;

    public InternalServerException(String message) {
        error = new ErrorDto(message, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
    }
}
