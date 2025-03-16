package ray1024.soa.navigatorservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ray1024.soa.navigatorservice.ejb.RemoteNavigatorService;
import ray1024.soa.navigatorservice.model.dto.NavigatorCreateRouteDto;
import ray1024.soa.navigatorservice.model.dto.RouteDto;
import ray1024.soa.navigatorservice.model.dto.RoutesDto;

@AllArgsConstructor
@RestController
public class NavigatorController {
    private final RemoteNavigatorService remoteNavigatorService;

    @GetMapping(value = "/api/v1/navigator/routes/{nameFrom}/{nameTo}/{orderBy}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<RoutesDto> getRoutes(@PathVariable String nameFrom,
                                               @PathVariable String nameTo,
                                               @PathVariable String orderBy) {
        return ResponseEntity.ok(
                RoutesDto.builder()
                        .routes(remoteNavigatorService.getRoutesWithLocationNamesSorted(nameFrom, nameTo, orderBy))
                        .build());
    }

    @PostMapping(value = "/api/v1/navigator/route/add/{name-from}/{name-to}/{distance}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public RouteDto addRoute(
            @PathVariable("name-from") String nameFrom,
            @PathVariable("name-to") String nameTo,
            @PathVariable float distance,
            @RequestBody NavigatorCreateRouteDto navigatorCreateRouteRequest
    ) {
        return remoteNavigatorService.createRouteByLocationsNames(
                nameFrom,
                nameTo,
                distance,
                navigatorCreateRouteRequest.getCoordinates(),
                navigatorCreateRouteRequest.getName()
        );
    }
}
