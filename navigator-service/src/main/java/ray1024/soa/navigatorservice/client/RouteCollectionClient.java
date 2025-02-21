package ray1024.soa.navigatorservice.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ray1024.soa.navigatorservice.exception.CollectionServiceAccessException;
import ray1024.soa.navigatorservice.exception.InternalServerException;
import ray1024.soa.navigatorservice.exception.InvalidQueryParamException;
import ray1024.soa.navigatorservice.exception.OtherErrorException;
import ray1024.soa.navigatorservice.model.dto.ErrorDto;
import ray1024.soa.navigatorservice.model.dto.RouteDto;
import ray1024.soa.navigatorservice.model.response.ErrorResponse;
import ray1024.soa.navigatorservice.model.response.InvalidParamsResponse;
import ray1024.soa.navigatorservice.model.response.RouteCollectionResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class RouteCollectionClient {
    private final static String COLLECTION_SERVICE_BASE_URL = "http://localhost:22398/api/v1/routes";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<RouteDto> getAllRoutes(int pageSize, int pageNumber, String sort, String filter) {
        try {
            RouteCollectionResponse response = restTemplate.getForObject(
                    COLLECTION_SERVICE_BASE_URL +
                            "?size=" +
                            pageSize +
                            "&page=" +
                            pageNumber +
                            "&sort=" +
                            sort +
                            "&filter=" +
                            filter,
                    RouteCollectionResponse.class
            );
            return response.getRoutes();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                InvalidParamsResponse response = e.getResponseBodyAs(InvalidParamsResponse.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                                    .build())
                            .build();
                }
                throw InvalidQueryParamException.builder()
                        .invalidParams(response.getInvalidParams())
                        .error(response.getError())
                        .build();
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                ErrorResponse response = e.getResponseBodyAs(ErrorResponse.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                                    .build())
                            .build();
                }
                throw OtherErrorException.builder()
                        .error(ErrorDto.builder()
                                .message(response.getMessage())
                                .time(response.getTime())
                                .build())
                        .build();
            } else {
                throw CollectionServiceAccessException.builder()
                        .error(ErrorDto.builder()
                                .message("Unknown response status: " + e.getStatusCode())
                                .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                                .build())
                        .build();
            }
        } catch (HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                ErrorResponse response = e.getResponseBodyAs(ErrorResponse.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                                    .build())
                            .build();
                }
                throw InternalServerException.builder()
                        .error(ErrorDto.builder()
                                .message(response.getMessage())
                                .time(response.getTime())
                                .build())
                        .build();
            } else {
                throw CollectionServiceAccessException.builder()
                        .error(ErrorDto.builder()
                                .message("Unknown response status: " + e.getStatusCode())
                                .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                                .build())
                        .build();
            }
        } catch (Exception e) {
            throw CollectionServiceAccessException.builder()
                    .error(ErrorDto.builder()
                            .message("Can't call other service to get routes" + e.getMessage())
                            .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                            .build())
                    .build();
        }
    }

    public RouteDto createRoute(RouteDto routeDto) {
        try {
            RouteDto response = restTemplate.postForObject(
                    COLLECTION_SERVICE_BASE_URL,
                    routeDto,
                    RouteDto.class
            );
            return response;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                InvalidParamsResponse response = e.getResponseBodyAs(InvalidParamsResponse.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                                    .build())
                            .build();
                }
                throw InvalidQueryParamException.builder()
                        .invalidParams(response.getInvalidParams())
                        .error(response.getError())
                        .build();
            } else {
                throw CollectionServiceAccessException.builder()
                        .error(ErrorDto.builder()
                                .message("Unknown response status: " + e.getStatusCode())
                                .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                                .build())
                        .build();
            }
        } catch (HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                ErrorResponse response = e.getResponseBodyAs(ErrorResponse.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                                    .build())
                            .build();
                }
                throw InternalServerException.builder()
                        .error(ErrorDto.builder()
                                .message(response.getMessage())
                                .time(response.getTime())
                                .build())
                        .build();
            } else {
                throw CollectionServiceAccessException.builder()
                        .error(ErrorDto.builder()
                                .message("Unknown response status: " + e.getStatusCode())
                                .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                                .build())
                        .build();
            }
        } catch (Exception e) {
            throw CollectionServiceAccessException.builder()
                    .error(ErrorDto.builder()
                            .message("Can't call other service to get routes")
                            .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                            .build())
                    .build();
        }
    }
}
