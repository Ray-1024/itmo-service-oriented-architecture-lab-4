package ray1024.soa.navigatorservice.model.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JacksonXmlRootElement(localName = "route")
public class RouteDto {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    private CoordinatesDto coordinates; //Поле не может быть null

    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private LocationDto from; //Поле может быть null

    private LocationDto to; //Поле не может быть null

    private int distance; //Значение поля должно быть больше 1
}
