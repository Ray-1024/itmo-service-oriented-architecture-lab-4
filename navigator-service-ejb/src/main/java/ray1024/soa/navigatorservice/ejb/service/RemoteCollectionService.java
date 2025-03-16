package ray1024.soa.navigatorservice.ejb.service;

import jakarta.ejb.Remote;
import ray1024.soa.navigatorservice.model.dto.RouteDto;

import java.util.List;

@Remote
public interface RemoteCollectionService {
    List<RouteDto> getAll(int page, int size, String sort, String filter);

    RouteDto create(RouteDto routeDto);
}
