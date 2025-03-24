package ray1024.soa.collectionservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.collectionservice.model.dto.CoordinatesDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "coordinates")
public class CoordinatesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Max(value = 510)
    private long x; //Максимальное значение поля: 510

    @NotNull
    private Long y; //Поле не может быть null

    public static CoordinatesEntity fromDto(CoordinatesDto coordinatesDto) {
        if (coordinatesDto == null) return null;
        return CoordinatesEntity.builder()
                .id(null)
                .x(coordinatesDto.getX())
                .y(coordinatesDto.getY())
                .build();
    }
}
