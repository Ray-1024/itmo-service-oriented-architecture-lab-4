package ray1024.soa.collectionservice.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ray1024.soa.collectionservice.exception.InvalidQueryParamException;
import ray1024.soa.collectionservice.model.dto.CountDto;
import ray1024.soa.collectionservice.model.dto.GroupsInfoDto;
import ray1024.soa.collectionservice.model.dto.RoutesDto;
import ray1024.soa.collectionservice.service.RouteService;

import static ray1024.soa.collectionservice.config.WsConfig.NAMESPACE_URI;

@Endpoint
@AllArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetRoutesRequest")
    @ResponsePayload
    public GetRoutesResponse getAll(@RequestPayload GetRoutesRequest request) {
        if (size == null) size = 5;
        if (page == null) page = 1;
        return RoutesDto.builder()
                .routes(routeService.getAllRoutes(page, size, sort, filter))
                .currentPageNumber(page)
                .build();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateRouteRequest")
    @ResponsePayload
    public CreateRouteResponse createRoute(@RequestPayload CreateRouteRequest request) {
        return routeService.createRoute(routeDto);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetRouteRequest")
    @ResponsePayload
    public GetRouteResponse getRoute(@RequestPayload GetRouteRequest request) {
        return routeService.getRoute(routeId);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PutRouteRequest")
    @ResponsePayload
    public PutRouteResponse modifyRoute(@RequestPayload PutRouteRequest request) {
        if (routeId == null)
            throw new InvalidQueryParamException("routeId", "routeId is incorrect", "Incorrect route id");
        if (routeDto == null)
            throw new InvalidQueryParamException("route", "route is incorrect", "Incorrect route");
        if (!routeId.equals(routeDto.getId()))
            throw new InvalidQueryParamException("routeId", "routeId are not equal to route.id from body", "Different id");
        return routeService.updateRoute(routeDto);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteRouteRequest")
    @ResponsePayload
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public DeleteRouteResponse deleteRoute(@RequestPayload DeleteRouteRequest request) {
        routeService.deleteRoute(routeId);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetGroupsInfoRequest")
    @ResponsePayload
    public GetGroupsInfoResponse getGroupsInfo(@RequestPayload GetGroupsInfoRequest request) {
        return GroupsInfoDto.builder()
                .groups(routeService.getGroupsInfo())
                .build();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEqualDistanceRoutesRequest")
    @ResponsePayload
    public GetEqualDistanceRoutesResponse getEqualDistanceRoutesCount(@RequestPayload GetEqualDistanceRoutesRequest request) {
        return CountDto.builder()
                .count((int) routeService.getEqualDistanceRoutesCount(distance))
                .build();
    }
}