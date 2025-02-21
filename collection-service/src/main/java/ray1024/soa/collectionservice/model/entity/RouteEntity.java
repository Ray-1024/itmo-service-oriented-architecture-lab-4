package ray1024.soa.collectionservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.collectionservice.model.dto.RouteDto;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "routes")
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1)
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull
    @NotBlank
    @Lob
    @Column(columnDefinition = "text")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CoordinatesEntity coordinates; //Поле не может быть null

    @NotNull
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocationEntity from; //Поле может быть null

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocationEntity to; //Поле не может быть null

    @Min(value = 2)
    private int distance; //Значение поля должно быть больше 1

    public static RouteEntity fromDto(RouteDto routeDto) {
        if (routeDto == null) return null;
        return RouteEntity.builder()
                .id(routeDto.getId())
                .name(routeDto.getName())
                .coordinates(CoordinatesEntity.fromDto(routeDto.getCoordinates()))
                .creationDate(routeDto.getCreationDate())
                .from(LocationEntity.fromDto(routeDto.getFrom()))
                .to(LocationEntity.fromDto(routeDto.getTo()))
                .distance(routeDto.getDistance())
                .build();
    }
}
