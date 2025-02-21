package ray1024.soa.navigatorservice.model.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.navigatorservice.model.dto.RouteDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JacksonXmlRootElement(localName = "response")
public class RouteCollectionResponse {
    private List<RouteDto> routes;
    private int currentPageNumber;
}
