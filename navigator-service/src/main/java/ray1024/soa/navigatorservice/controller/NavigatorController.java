package ray1024.soa.navigatorservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ray1024.soa.navigatorservice.model.dto.RouteDto;
import ray1024.soa.navigatorservice.model.request.NavigatorCreateRouteRequest;
import ray1024.soa.navigatorservice.model.response.RouteCollectionResponse;
import ray1024.soa.navigatorservice.service.NavigatorService;

@AllArgsConstructor
@RestController
public class NavigatorController {
    private final NavigatorService navigatorService;

    @GetMapping(value = "/api/v1/navigator/routes/{nameFrom}/{nameTo}/{orderBy}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<RouteCollectionResponse> getRoutes(@PathVariable String nameFrom,
                                                             @PathVariable String nameTo,
                                                             @PathVariable String orderBy) {
        return ResponseEntity.ok(
                RouteCollectionResponse.builder()
                        .routes(navigatorService.getRoutesWithLocationNamesSorted(nameFrom, nameTo, orderBy))
                        .build());
    }

    @PostMapping(value = "/api/v1/navigator/route/add/{name-from}/{name-to}/{distance}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public RouteDto addRoute(
            @PathVariable("name-from") String nameFrom,
            @PathVariable("name-to") String nameTo,
            @PathVariable float distance,
            @RequestBody NavigatorCreateRouteRequest navigatorCreateRouteRequest
    ) {
        return navigatorService.createRouteByLocationsNames(
                nameFrom,
                nameTo,
                distance,
                navigatorCreateRouteRequest.getCoordinates(),
                navigatorCreateRouteRequest.getName()
        );
    }
}
