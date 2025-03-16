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
@JacksonXmlRootElement(localName = "route")
public class RouteDto {
    @JacksonXmlProperty
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @JacksonXmlProperty
    @JsonProperty(required = true)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @JacksonXmlProperty
    @JsonProperty(required = true)
    private CoordinatesDto coordinates; //Поле не может быть null
    @JacksonXmlProperty
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @JacksonXmlProperty
    private LocationDto from; //Поле может быть null
    @JacksonXmlProperty
    @JsonProperty(required = true)
    private LocationDto to; //Поле не может быть null
    @JacksonXmlProperty
    @JsonProperty(required = true)
    private int distance; //Значение поля должно быть больше 1
}
