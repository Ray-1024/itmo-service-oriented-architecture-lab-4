package ray1024.soa.collectionservice.model.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.collectionservice.model.entity.RouteEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JacksonXmlRootElement(localName = "route")
public class RouteDto {
    @JacksonXmlProperty
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @JacksonXmlProperty
    private String name; //Поле не может быть null, Строка не может быть пустой
    @JacksonXmlProperty
    private CoordinatesDto coordinates; //Поле не может быть null
    @JacksonXmlProperty
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @JacksonXmlProperty
    private LocationDto from; //Поле может быть null
    @JacksonXmlProperty
    private LocationDto to; //Поле не может быть null
    @JacksonXmlProperty
    private int distance; //Значение поля должно быть больше 1

    public static RouteDto fromEntity(RouteEntity routeEntity) {
        if (routeEntity == null) return null;
        return RouteDto.builder()
                .id(routeEntity.getId() <= 0 ? null : routeEntity.getId())
                .name(routeEntity.getName())
                .coordinates(CoordinatesDto.fromEntity(routeEntity.getCoordinates()))
                .creationDate(routeEntity.getCreationDate())
                .from(LocationDto.fromEntity(routeEntity.getFrom()))
                .to(LocationDto.fromEntity(routeEntity.getTo()))
                .distance(routeEntity.getDistance())
                .build();
    }
}
