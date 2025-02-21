package ray1024.soa.navigatorservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ray1024.soa.navigatorservice.client.RouteCollectionClient;
import ray1024.soa.navigatorservice.model.dto.CoordinatesDto;
import ray1024.soa.navigatorservice.model.dto.LocationDto;
import ray1024.soa.navigatorservice.model.dto.RouteDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NavigatorService {
    private final RouteCollectionClient routeCollectionClient;

    public List<RouteDto> getRoutesWithLocationNamesSorted(String fromLocationName, String toLocationName, String sorting) {
        final ArrayList<RouteDto> routes = new ArrayList<>();
        final String filter = "from.name=" + fromLocationName;
        for (int pageNumber = 1; ; ++pageNumber) {
            List<RouteDto> page = routeCollectionClient.getAllRoutes(100, pageNumber, sorting, filter);

            if (page == null || page.isEmpty()) break;

            routes.addAll(page.stream().filter(routeDto -> Objects.equals(routeDto.getTo().getName(), toLocationName)).collect(Collectors.toList()));
        }
        return routes;
    }

    public RouteDto createRouteByLocationsNames(String fromLocationName,
                                                String toLocationName,
                                                float distance,
                                                CoordinatesDto coordinatesDto,
                                                String routeName
    ) {
        /*LocationDto from = null;
        LocationDto to = null;

        for (int pageNumber = 1; ; ++pageNumber) {
            List<RouteDto> page = routeCollectionClient.getAllRoutes(100, pageNumber, "", "");

            if (page.isEmpty()) break;

            for (RouteDto routeDto : page) {
                if (Objects.equals(routeDto.getFrom().getName(), fromLocationName)) {
                    from = routeDto.getFrom();
                }
                if (Objects.equals(routeDto.getTo().getName(), toLocationName)) {
                    to = routeDto.getTo();
                }
                if (Objects.nonNull(from) && Objects.nonNull(to)) {
                    break;
                }
            }
            if (Objects.nonNull(from) && Objects.nonNull(to)) {
                break;
            }

        }

        if (Objects.isNull(from) || Objects.isNull(to)) {
            String paramName = Objects.isNull(from) ? "name-from" : "name-to";
            throw InvalidInputException.builder()
                    .invalidParams(List.of(InvalidParamDto.builder()
                            .paramName(paramName)
                            .message("Location with this name " + paramName + " not found")
                            .build()
                    ))
                    .error(ErrorDto.builder()
                            .message("Invalid parameter name: " + paramName)
                            .time(Instant.now())
                            .build())
                    .build();
        }*/

        return routeCollectionClient.createRoute(
                RouteDto.builder()
                        .name(routeName)
                        .coordinates(coordinatesDto)
                        .from(LocationDto.builder().x(1).y(1).z(1).name(fromLocationName).build())
                        .to(LocationDto.builder().x(1).y(1).z(1).name(toLocationName).build())
                        .distance((int) distance)
                        .build()
        );
    }
}
