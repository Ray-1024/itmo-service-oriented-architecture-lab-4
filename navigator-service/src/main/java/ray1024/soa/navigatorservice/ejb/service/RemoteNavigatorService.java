package ray1024.soa.navigatorservice.ejb.service;

import ray1024.soa.navigatorservice.model.dto.CoordinatesDto;
import ray1024.soa.navigatorservice.model.dto.RouteDto;

import java.util.List;

public interface RemoteNavigatorService {
    List<RouteDto> getRoutesWithLocationNamesSorted(String fromLocationName, String toLocationName, String sorting);

    RouteDto createRouteByLocationsNames(String fromLocationName,
                                         String toLocationName,
                                         Float distance,
                                         CoordinatesDto coordinatesDto,
                                         String routeName
    );
}
