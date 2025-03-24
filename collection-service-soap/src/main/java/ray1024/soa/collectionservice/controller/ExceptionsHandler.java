package ray1024.soa.collectionservice.controller;

import org.springframework.web.bind.annotation.*;
import ray1024.soa.collectionservice.exception.*;
import ray1024.soa.collectionservice.model.dto.ErrorDto;
import ray1024.soa.collectionservice.model.dto.InvalidParamsDto;

import static org.springframework.http.HttpStatus.*;

@ResponseBody
@ControllerAdvice
@RequestMapping(produces = org.springframework.http.MediaType.APPLICATION_XML_VALUE)
public class ExceptionsHandler {

    @ExceptionHandler({
            InternalServerException.class,
    })
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorDto handleInternalServerException(InternalServerException e) {
        return ErrorDto.builder()
                .message(e.getError().getMessage())
                .time(e.getError().getTime())
                .build();
    }

    @ExceptionHandler({
            InvalidInputException.class,
    })
    @ResponseStatus(BAD_REQUEST)
    public InvalidParamsDto handleInvalidInputException(InvalidInputException e) {
        return InvalidParamsDto.builder()
                .invalidParams(e.getInvalidParams())
                .error(e.getError())
                .build();
    }

    @ExceptionHandler({
            InvalidBodyParamException.class,
    })
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public InvalidBodyParamException handleInvalidBodyParamException(InvalidBodyParamException e) {
        return InvalidBodyParamException.builder()
                .invalidParams(e.getInvalidParams())
                .error(e.getError())
                .build();
    }

    @ExceptionHandler({
            InvalidQueryParamException.class,
    })
    @ResponseStatus(BAD_REQUEST)
    public InvalidParamsDto handleInvalidQueryParamException(InvalidQueryParamException e) {
        return InvalidParamsDto.builder()
                .invalidParams(e.getInvalidParams())
                .error(e.getError())
                .build();
    }

    @ExceptionHandler({
            OtherErrorException.class,
    })
    @ResponseStatus(NOT_FOUND)
    public ErrorDto handleOtherErrorException(OtherErrorException e) {
        return ErrorDto.builder()
                .message(e.getError().getMessage())
                .time(e.getError().getTime())
                .build();
    }
}
