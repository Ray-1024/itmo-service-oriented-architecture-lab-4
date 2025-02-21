package ray1024.soa.navigatorservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.navigatorservice.model.dto.RouteDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RouteRequest {
    private RouteDto route;
}
