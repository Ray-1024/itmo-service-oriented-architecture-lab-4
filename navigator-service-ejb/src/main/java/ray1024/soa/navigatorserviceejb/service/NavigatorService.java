package ray1024.soa.navigatorserviceejb.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ray1024.soa.navigatorservice.model.dto.CoordinatesDto;
import ray1024.soa.navigatorservice.model.dto.LocationDto;
import ray1024.soa.navigatorservice.model.dto.RouteDto;
import ray1024.soa.navigatorservice.old.client.RouteCollectionClient;

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
