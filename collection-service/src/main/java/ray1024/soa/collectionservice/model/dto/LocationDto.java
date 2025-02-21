package ray1024.soa.collectionservice.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.collectionservice.model.entity.LocationEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationDto {
    @XmlElement(required = true)
    private int x;
    @XmlElement(required = true)
    private int y;
    @XmlElement(required = true)
    private double z;
    @XmlElement(required = true)
    private String name; //Длина строки не должна быть больше 452, Поле не может быть null

    public static LocationDto fromEntity(LocationEntity locationEntity) {
        if (locationEntity == null) return null;
        return LocationDto.builder()
                .x(locationEntity.getX())
                .y(locationEntity.getY())
                .z(locationEntity.getZ())
                .name(locationEntity.getName())
                .build();
    }
}
