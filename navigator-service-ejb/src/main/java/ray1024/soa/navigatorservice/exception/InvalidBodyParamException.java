package ray1024.soa.navigatorservice.exception;

import lombok.*;
import ray1024.soa.navigatorservice.model.dto.ErrorDto;
import ray1024.soa.navigatorservice.model.dto.InvalidParamDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvalidBodyParamException extends RuntimeException {
    private List<InvalidParamDto> invalidParams;
    private ErrorDto error;

    public InvalidBodyParamException(String paramName, String paramMessage, String errorMessage) {
        invalidParams = List.of(
                InvalidParamDto.builder()
                        .paramName(paramName)
                        .message(paramMessage)
                        .build()
        );
        error = ErrorDto.builder()
                .message(errorMessage)
                .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                .build();
    }
}
