package ray1024.soa.navigatorservice.model.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JacksonXmlRootElement(localName = "response")
public class NavigatorCreateRouteDto {
    @JacksonXmlProperty
    private CoordinatesDto coordinates;

    @JacksonXmlProperty
    private String name;
}
