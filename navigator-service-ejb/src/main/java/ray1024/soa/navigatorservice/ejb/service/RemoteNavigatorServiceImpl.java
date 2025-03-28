package ray1024.soa.navigatorservice.ejb.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.jboss.ejb3.annotation.Pool;
import ray1024.soa.navigatorservice.client.CollectionServiceClient;
import ray1024.soa.navigatorservice.model.dto.CoordinatesDto;
import ray1024.soa.navigatorservice.model.dto.LocationDto;
import ray1024.soa.navigatorservice.model.dto.RouteDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Stateless(name = "RemoteNavigatorService")
@Pool("remote-navigator-service-pool")
public class RemoteNavigatorServiceImpl implements RemoteNavigatorService {

    @Inject
    private CollectionServiceClient collectionServiceClient;

    @Override
    public List<RouteDto> getRoutesWithLocationNamesSorted(String fromLocationName, String toLocationName, String sorting) {
        final ArrayList<RouteDto> routes = new ArrayList<>();
        final String filter = "from.name=" + fromLocationName;
        for (int pageNumber = 1; ; ++pageNumber) {
            List<RouteDto> page = collectionServiceClient.getAllRoutes(100, pageNumber, sorting, filter);

            if (page == null || page.isEmpty()) break;

            routes.addAll(page.stream().filter(routeDto -> Objects.equals(routeDto.getTo().getName(), toLocationName)).toList());
        }
        return routes;
    }

    @Override
    public RouteDto createRouteByLocationsNames(String fromLocationName,
                                                String toLocationName,
                                                Float distance,
                                                CoordinatesDto coordinatesDto,
                                                String routeName
    ) {
        return collectionServiceClient.createRoute(
                RouteDto.builder()
                        .name(routeName)
                        .coordinates(coordinatesDto)
                        .from(LocationDto.builder().x(1).y(1).z(1).name(fromLocationName).build())
                        .to(LocationDto.builder().x(1).y(1).z(1).name(toLocationName).build())
                        .distance((int) distance.floatValue())
                        .build()
        );
    }
}
