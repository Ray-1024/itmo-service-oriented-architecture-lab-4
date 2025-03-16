package ray1024.soa.navigatorservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ray1024.soa.navigatorservice.exception.*;
import ray1024.soa.navigatorservice.model.dto.ErrorDto;
import ray1024.soa.navigatorservice.model.dto.InvalidParamsDto;

import static org.springframework.http.HttpStatus.*;

@ResponseBody
@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({
            CollectionServiceAccessException.class,
    })
    public ResponseEntity<ErrorDto> handleCollectionServiceAccessException(CollectionServiceAccessException e) {
        return ResponseEntity.status(SERVICE_UNAVAILABLE)
                .body(ErrorDto.builder()
                        .message(e.getError().getMessage())
                        .time(e.getError().getTime())
                        .build());
    }

    @ExceptionHandler({
            InternalServerException.class,
    })
    public ResponseEntity<ErrorDto> handleInternalServerException(InternalServerException e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ErrorDto.builder()
                        .message(e.getError().getMessage())
                        .time(e.getError().getTime())
                        .build()
        );
    }

    @ExceptionHandler({
            InvalidInputException.class,
    })
    public ResponseEntity<InvalidParamsDto> handleInvalidInputException(InvalidInputException e) {
        return ResponseEntity.badRequest().body(InvalidParamsDto.builder()
                .invalidParams(e.getInvalidParams())
                .error(e.getError())
                .build());
    }

    @ExceptionHandler({
            InvalidQueryParamException.class,
    })
    public ResponseEntity<InvalidParamsDto> handleInvalidQueryParamException(InvalidQueryParamException e) {
        return ResponseEntity.badRequest().body(
                InvalidParamsDto.builder()
                        .invalidParams(e.getInvalidParams())
                        .error(e.getError())
                        .build()
        );
    }

    @ExceptionHandler({
            OtherErrorException.class,
    })
    public ResponseEntity<ErrorDto> handleOtherErrorException(OtherErrorException e) {
        return ResponseEntity.status(NOT_FOUND).body(
                ErrorDto.builder()
                        .message(e.getError().getMessage())
                        .time(e.getError().getTime())
                        .build()
        );
    }
}
