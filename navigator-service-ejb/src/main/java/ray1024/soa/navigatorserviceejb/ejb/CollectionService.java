package ray1024.soa.navigatorserviceejb.ejb;

import jakarta.ejb.Remote;
import ray1024.soa.navigatorservice.model.dto.RouteDto;

@Remote
public interface CollectionService {
    List<RouteDto> getAll(int page, int size, String sort, String filter);

    RouteDto create(RouteDto routeDto);
}
