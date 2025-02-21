package ray1024.soa.collectionservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.collectionservice.model.dto.RouteDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RouteResponse {
    private RouteDto route;
}
