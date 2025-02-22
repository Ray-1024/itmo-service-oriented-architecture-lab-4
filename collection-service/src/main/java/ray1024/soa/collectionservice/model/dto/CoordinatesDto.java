package ray1024.soa.collectionservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.collectionservice.model.entity.CoordinatesEntity;

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

    public static CoordinatesDto fromEntity(CoordinatesEntity coordinatesEntity) {
        if (coordinatesEntity == null) return null;
        return CoordinatesDto.builder()
                .x(coordinatesEntity.getX())
                .y(coordinatesEntity.getY())
                .build();
    }
}
