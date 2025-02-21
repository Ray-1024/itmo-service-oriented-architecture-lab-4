package ray1024.soa.navigatorservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.navigatorservice.model.dto.CoordinatesDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NavigatorCreateRouteRequest {
    private CoordinatesDto coordinates;
    private String name;
}
