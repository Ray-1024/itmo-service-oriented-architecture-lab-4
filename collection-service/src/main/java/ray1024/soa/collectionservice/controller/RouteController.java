package ray1024.soa.collectionservice.controller;


import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ray1024.soa.collectionservice.exception.InvalidQueryParamException;
import ray1024.soa.collectionservice.model.dto.CountDto;
import ray1024.soa.collectionservice.model.dto.GroupsInfoDto;
import ray1024.soa.collectionservice.model.dto.RouteDto;
import ray1024.soa.collectionservice.model.dto.RoutesDto;
import ray1024.soa.collectionservice.service.RouteService;

@RequestMapping(value = "/api/v1/routes",
        consumes = org.springframework.http.MediaType.APPLICATION_XML_VALUE,
        produces = org.springframework.http.MediaType.APPLICATION_XML_VALUE)
@RestController
@AllArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping
    public RoutesDto getAll(@RequestParam("size") @DefaultValue(value = "5") Integer size,
                            @RequestParam("page") @DefaultValue(value = "1") Integer page,
                            @RequestParam("sort") @DefaultValue(value = "") String sort,
                            @RequestParam("filter") @DefaultValue(value = "") String filter) {
        if (size == null) size = 5;
        if (page == null) page = 1;
        return RoutesDto.builder()
                .routes(routeService.getAllRoutes(page, size, sort, filter))
                .currentPageNumber(page)
                .build();
    }

    @PostMapping
    public RouteDto createRoute(@RequestBody RouteDto routeDto) {
        return routeService.createRoute(routeDto);
    }

    @GetMapping("/{routeId}")
    public RouteDto getRoute(@PathVariable("routeId") Long routeId) {
        return routeService.getRoute(routeId);
    }

    @PutMapping("/{routeId}")
    public RouteDto modifyRoute(@PathVariable("routeId") Long routeId,
                                @RequestBody RouteDto routeDto) {
        if (routeId == null)
            throw new InvalidQueryParamException("routeId", "routeId is incorrect", "Incorrect route id");
        if (routeDto == null)
            throw new InvalidQueryParamException("route", "route is incorrect", "Incorrect route");
        if (!routeId.equals(routeDto.getId()))
            throw new InvalidQueryParamException("routeId", "routeId are not equal to route.id from body", "Different id");
        return routeService.updateRoute(routeDto);
    }

    @DeleteMapping("/{routeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable("routeId") long routeId) {
        routeService.deleteRoute(routeId);
    }

    @GetMapping("/name-groups-info")
    public GroupsInfoDto getGroupsInfo() {
        return GroupsInfoDto.builder()
                .groups(routeService.getGroupsInfo())
                .build();
    }

    @GetMapping("/with-distance-count")
    public CountDto getEqualDistanceRoutesCount(@RequestParam("distance") float distance) {
        return CountDto.builder()
                .count((int) routeService.getEqualDistanceRoutesCount(distance))
                .build();
    }
}