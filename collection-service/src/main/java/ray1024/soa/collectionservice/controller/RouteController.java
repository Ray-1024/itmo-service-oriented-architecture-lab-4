package ray1024.soa.collectionservice.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ray1024.soa.collectionservice.exception.InvalidQueryParamException;
import ray1024.soa.collectionservice.model.dto.ErrorDto;
import ray1024.soa.collectionservice.model.dto.InvalidParamDto;
import ray1024.soa.collectionservice.model.dto.RouteDto;
import ray1024.soa.collectionservice.model.response.CountResponse;
import ray1024.soa.collectionservice.model.response.GroupsInfoResponse;
import ray1024.soa.collectionservice.model.response.RouteCollectionResponse;
import ray1024.soa.collectionservice.service.RouteService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/api/v1/routes")
@RestController
@AllArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping(
            consumes = org.springframework.http.MediaType.APPLICATION_XML_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_XML_VALUE)
    public RouteCollectionResponse getAll(@PathParam("size") @DefaultValue(value = "5") int size,
                                          @PathParam("page") @DefaultValue(value = "1") int page,
                                          @PathParam("sort") @DefaultValue(value = "") String sort,
                                          @PathParam("filter") @DefaultValue(value = "") String filter) {
        return RouteCollectionResponse.builder()
                .routes(routeService.getAllRoutes(page, size, sort, filter))
                .currentPageNumber(page)
                .build();
    }

    @PostMapping(
            consumes = org.springframework.http.MediaType.APPLICATION_XML_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_XML_VALUE)
    public RouteDto createRoute(RouteDto routeDto) {
        return routeService.createRoute(routeDto);
    }

    @GetMapping(value = "/{routeId}",
            consumes = org.springframework.http.MediaType.APPLICATION_XML_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_XML_VALUE)
    public RouteDto getRoute(@PathVariable("routeId") long routeId) {
        return routeService.getRoute(routeId);
    }

    @PutMapping(value = "/{routeId}",
            consumes = org.springframework.http.MediaType.APPLICATION_XML_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_XML_VALUE)
    public RouteDto modifyRoute(@PathVariable("routeId") long routeId,
                                RouteDto routeDto) {
        if (routeId != routeDto.getId()) throw InvalidQueryParamException.builder()
                .invalidParams(List.of(
                        InvalidParamDto.builder()
                                .paramName("routeId")
                                .message("routeId are not equal to route.id from body")
                                .build()
                ))
                .error(ErrorDto.builder().message("Different id")
                        .time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
                        .build())
                .build();
        return routeService.updateRoute(routeDto);
    }

    @DeleteMapping(value = "/{routeId}",
            consumes = org.springframework.http.MediaType.APPLICATION_XML_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable("routeId") long routeId) {
        routeService.deleteRoute(routeId);
    }

    @GetMapping(value = "/name-groups-info",
            consumes = org.springframework.http.MediaType.APPLICATION_XML_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_XML_VALUE)
    public GroupsInfoResponse getGroupsInfo() {
        return GroupsInfoResponse.builder()
                .groups(routeService.getGroupsInfo())
                .build();
    }

    @GetMapping(value = "/with-distance-count",
            consumes = org.springframework.http.MediaType.APPLICATION_XML_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_XML_VALUE)
    public CountResponse getEqualDistanceRoutesCount(@PathParam("distance") float distance) {
        return CountResponse.builder()
                .count((int) routeService.getEqualDistanceRoutesCount(distance))
                .build();
    }
}