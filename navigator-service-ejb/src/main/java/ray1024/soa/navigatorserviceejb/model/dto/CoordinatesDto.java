package ray1024.soa.navigatorserviceejb.model.dto;

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
@JacksonXmlRootElement(localName = "coordinates")
public class CoordinatesDto {
    @JacksonXmlProperty
    @JsonProperty(required = true)
    private long x; //Максимальное значение поля: 510
    @JacksonXmlProperty
    @JsonProperty(required = true)
    private Long y; //Поле не может быть null
}
