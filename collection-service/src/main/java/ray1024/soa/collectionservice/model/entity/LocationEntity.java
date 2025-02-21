package ray1024.soa.collectionservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.collectionservice.model.dto.LocationDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "locations")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;

    private int y;

    private double z;

    @Size(max = 452)
    @NotNull
    private String name; //Длина строки не должна быть больше 452, Поле не может быть null

    public static LocationEntity fromDto(LocationDto locationDto) {
        if (locationDto == null) return null;
        return LocationEntity.builder()
                .id(null)
                .x(locationDto.getX())
                .y(locationDto.getY())
                .z(locationDto.getZ())
                .name(locationDto.getName())
                .build();
    }
}
