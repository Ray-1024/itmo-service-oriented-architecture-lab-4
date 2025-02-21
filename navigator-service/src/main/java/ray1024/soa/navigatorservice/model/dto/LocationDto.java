package ray1024.soa.navigatorservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocationDto {
    private int x;
    private int y;
    private double z;
    private String name; //Длина строки не должна быть больше 452, Поле не может быть null
}
