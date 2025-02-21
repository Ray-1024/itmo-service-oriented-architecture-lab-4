package ray1024.soa.collectionservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ray1024.soa.collectionservice.exception.InternalServerException;
import ray1024.soa.collectionservice.exception.InvalidInputException;
import ray1024.soa.collectionservice.exception.InvalidQueryParamException;
import ray1024.soa.collectionservice.exception.OtherErrorException;
import ray1024.soa.collectionservice.model.response.ErrorResponse;
import ray1024.soa.collectionservice.model.response.InvalidParamsResponse;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseBody
@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({
            InternalServerException.class,
    })
    public ResponseEntity<ErrorResponse> handleInternalServerException(InternalServerException e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ErrorResponse.builder()
                        .message(e.getError().getMessage())
                        .time(e.getError().getTime())
                        .build()
        );
    }

    @ExceptionHandler({
            InvalidInputException.class,
    })
    public ResponseEntity<InvalidParamsResponse> handleInvalidInputException(InvalidInputException e) {
        return ResponseEntity.badRequest().body(InvalidParamsResponse.builder()
                .invalidParams(e.getInvalidParams())
                .error(e.getError())
                .build());
    }

    @ExceptionHandler({
            InvalidQueryParamException.class,
    })
    public ResponseEntity<InvalidParamsResponse> handleInvalidQueryParamException(InvalidQueryParamException e) {
        return ResponseEntity.badRequest().body(
                InvalidParamsResponse.builder()
                        .invalidParams(e.getInvalidParams())
                        .error(e.getError())
                        .build()
        );
    }

    @ExceptionHandler({
            OtherErrorException.class,
    })
    public ResponseEntity<ErrorResponse> handleOtherErrorException(OtherErrorException e) {
        return ResponseEntity.status(NOT_FOUND).body(
                ErrorResponse.builder()
                        .message(e.getError().getMessage())
                        .time(e.getError().getTime())
                        .build()
        );
    }
}
