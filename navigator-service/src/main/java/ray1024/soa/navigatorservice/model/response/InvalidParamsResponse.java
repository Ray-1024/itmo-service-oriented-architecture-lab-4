package ray1024.soa.navigatorservice.model.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ray1024.soa.navigatorservice.model.dto.ErrorDto;
import ray1024.soa.navigatorservice.model.dto.InvalidParamDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JacksonXmlRootElement(localName = "response")
public class InvalidParamsResponse {
    private List<InvalidParamDto> invalidParams;
    private ErrorDto error;
}
