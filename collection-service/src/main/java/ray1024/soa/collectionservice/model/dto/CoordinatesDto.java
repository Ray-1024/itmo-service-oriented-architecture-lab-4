package ray1024.soa.collectionservice.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.collectionservice.model.entity.CoordinatesEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class CoordinatesDto {
    @XmlElement(required = true)
    private long x; //Максимальное значение поля: 510
    @XmlElement(required = true)
    private Long y; //Поле не может быть null

    public static CoordinatesDto fromEntity(CoordinatesEntity coordinatesEntity) {
        if (coordinatesEntity == null) return null;
        return CoordinatesDto.builder()
                .x(coordinatesEntity.getX())
                .y(coordinatesEntity.getY())
                .build();
    }
}
