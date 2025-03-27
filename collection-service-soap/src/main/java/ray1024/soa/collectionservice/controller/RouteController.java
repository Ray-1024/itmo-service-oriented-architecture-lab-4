package ray1024.soa.collectionservice.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ray1024.soa.collectionservice.exception.InvalidQueryParamException;
import ray1024.soa.collectionservice.service.RouteService;
import ray1024.soa.collectionservice.soap.gen.*;

import static ray1024.soa.collectionservice.config.WsConfig.NAMESPACE_URI;

@Endpoint
@AllArgsConstructor
public class RouteController {
    private final RouteService routeService;

    private CoordinatesDto fromMyCoordinatesDto(ray1024.soa.collectionservice.model.dto.CoordinatesDto coordinatesDto) {
        if (coordinatesDto == null) return null;
        CoordinatesDto dto = new CoordinatesDto();
        dto.setX(coordinatesDto.getX());
        dto.setY(coordinatesDto.getY());
        return dto;
    }

    private LocationDto fromMyCoordinatesDto(ray1024.soa.collectionservice.model.dto.LocationDto locationDto) {
        if (locationDto == null) return null;
        LocationDto dto = new LocationDto();
        dto.setX(locationDto.getX());
        dto.setY(locationDto.getY());
        dto.setZ(locationDto.getZ());
        dto.setName(locationDto.getName());
        return dto;
    }

    private RouteDto fromMyRouteDto(ray1024.soa.collectionservice.model.dto.RouteDto routeDto) {
        if (routeDto == null) return null;
        RouteDto dto = new RouteDto();
        dto.setId(routeDto.getId());
        dto.setName(routeDto.getName());
        dto.setCoordinates(fromMyCoordinatesDto(routeDto.getCoordinates()));
        dto.setCreationDate(routeDto.getCreationDate());
        dto.setFrom(fromMyCoordinatesDto(routeDto.getFrom()));
        dto.setTo(fromMyCoordinatesDto(routeDto.getTo()));
        dto.setDistance(routeDto.getDistance());
        return dto;
    }

    private ray1024.soa.collectionservice.model.dto.CoordinatesDto toMyCoordinatesDto(CoordinatesDto coordinatesDto) {
        if (coordinatesDto == null) return null;
        ray1024.soa.collectionservice.model.dto.CoordinatesDto dto = new ray1024.soa.collectionservice.model.dto.CoordinatesDto();
        dto.setX(coordinatesDto.getX());
        dto.setY(coordinatesDto.getY());
        return dto;
    }

    private ray1024.soa.collectionservice.model.dto.LocationDto toMyCoordinatesDto(LocationDto locationDto) {
        if (locationDto == null) return null;
        ray1024.soa.collectionservice.model.dto.LocationDto dto = new ray1024.soa.collectionservice.model.dto.LocationDto();
        dto.setX(locationDto.getX());
        dto.setY(locationDto.getY());
        dto.setZ(locationDto.getZ());
        dto.setName(locationDto.getName());
        return dto;
    }

    private ray1024.soa.collectionservice.model.dto.RouteDto toMyRouteDto(RouteDto routeDto) {
        if (routeDto == null) return null;
        ray1024.soa.collectionservice.model.dto.RouteDto dto = new ray1024.soa.collectionservice.model.dto.RouteDto();
        dto.setId(routeDto.getId());
        dto.setName(routeDto.getName());
        dto.setCoordinates(toMyCoordinatesDto(routeDto.getCoordinates()));
        dto.setCreationDate(routeDto.getCreationDate());
        dto.setFrom(toMyCoordinatesDto(routeDto.getFrom()));
        dto.setTo(toMyCoordinatesDto(routeDto.getTo()));
        dto.setDistance(routeDto.getDistance());
        return dto;
    }

    private GroupInfoDto fromMyGroupInfoDto(ray1024.soa.collectionservice.model.dto.GroupInfoDto groupInfoDto) {
        if (groupInfoDto == null) return null;
        GroupInfoDto dto = new GroupInfoDto();
        dto.setName(groupInfoDto.getName());
        dto.setCount(groupInfoDto.getCount());
        return dto;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetRoutesRequest")
    @ResponsePayload
    public GetRoutesResponse getAll(@RequestPayload GetRoutesRequest request) {
        if (request.getSize() == 0) request.setSize(5);
        if (request.getPage() == 0) request.setPage(1);
        GetRoutesResponse response = new GetRoutesResponse();
        response.getRoutes().addAll(routeService.getAllRoutes(request.getPage(), request.getSize(),
                request.getSort(), request.getFilter()).stream().map(this::fromMyRouteDto).toList());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateRouteRequest")
    @ResponsePayload
    public CreateRouteResponse createRoute(@RequestPayload CreateRouteRequest request) {
        CreateRouteResponse response = new CreateRouteResponse();
        response.setRoute(fromMyRouteDto(routeService.createRoute(toMyRouteDto(request.getRoute()))));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetRouteRequest")
    @ResponsePayload
    public GetRouteResponse getRoute(@RequestPayload GetRouteRequest request) {
        GetRouteResponse response = new GetRouteResponse();
        response.setRoute(fromMyRouteDto(routeService.getRoute(request.getRouteId())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PutRouteRequest")
    @ResponsePayload
    public PutRouteResponse modifyRoute(@RequestPayload PutRouteRequest request) {
//        if (request.getRouteId() == null)
//            throw new InvalidQueryParamException("routeId", "routeId is incorrect", "Incorrect route id");
        if (request.getRouteDto() == null)
            throw new InvalidQueryParamException("route", "route is incorrect", "Incorrect route");
        if (request.getRouteId() != (request.getRouteDto().getId()))
            throw new InvalidQueryParamException("routeId", "routeId are not equal to route.id from body", "Different id");
        PutRouteResponse response = new PutRouteResponse();
        response.setRoute(fromMyRouteDto(routeService.updateRoute(toMyRouteDto(request.getRouteDto()))));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteRouteRequest")
    @ResponsePayload
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public DeleteRouteResponse deleteRoute(@RequestPayload DeleteRouteRequest request) {
        routeService.deleteRoute(request.getRouteId());
        return new DeleteRouteResponse();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetGroupsInfoRequest")
    @ResponsePayload
    public GetGroupsInfoResponse getGroupsInfo(@RequestPayload GetGroupsInfoRequest request) {
        GetGroupsInfoResponse response = new GetGroupsInfoResponse();
        response.getGroups().addAll(routeService.getGroupsInfo().stream().map(this::fromMyGroupInfoDto).toList());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEqualDistanceRoutesRequest")
    @ResponsePayload
    public GetEqualDistanceRoutesResponse getEqualDistanceRoutesCount(@RequestPayload GetEqualDistanceRoutesRequest request) {
        GetEqualDistanceRoutesResponse response = new GetEqualDistanceRoutesResponse();
        response.setCount((int) routeService.getEqualDistanceRoutesCount(request.getDistance()));
        return response;
    }
}