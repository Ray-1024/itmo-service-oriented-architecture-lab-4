package ray1024.soa.navigatorserviceejb.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ray1024.soa.navigatorserviceejb.exception.CollectionServiceAccessException;
import ray1024.soa.navigatorserviceejb.model.dto.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class RouteCollectionClient {
    private final static String COLLECTION_SERVICE_BASE_URL = "https://localhost:22400/api/v1/routes";

    @Inject
    private final RestTemplate restTemplate;

    public List<RouteDto> getAllRoutes(int pageSize, int pageNumber, String sort, String filter) {
        try {
            RoutesDto response = restTemplate.getForObject(
                    COLLECTION_SERVICE_BASE_URL +
                            "?size=" +
                            pageSize +
                            "&page=" +
                            pageNumber +
                            "&sort=" +
                            sort +
                            "&filter=" +
                            filter,
                    RoutesDto.class
            );
            return response.getRoutes();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                InvalidParamsDto response = e.getResponseBodyAs(InvalidParamsDto.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                                    .build())
                            .build();
                }
                throw InvalidParamsDto.builder()
                        .invalidParams(response.getInvalidParams())
                        .error(response.getError())
                        .build();
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                ErrorDto response = e.getResponseBodyAs(ErrorDto.class);
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
