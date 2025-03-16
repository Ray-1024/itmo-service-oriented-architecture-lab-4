package ray1024.soa.navigatorservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JacksonXmlRootElement(localName = "location")
public class LocationDto {
    @JacksonXmlProperty
    @JsonProperty(required = true)
    private int x;
    @JacksonXmlProperty
    @JsonProperty(required = true)
    private int y;
    @JacksonXmlProperty
    @JsonProperty(required = true)
    private double z;
    @JacksonXmlProperty
    @JsonProperty(required = true)
    private String name; //Длина строки не должна быть больше 452, Поле не может быть null
}
