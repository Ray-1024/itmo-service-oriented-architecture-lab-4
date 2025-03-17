package ray1024.soa.navigatorservice.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class JacksonConfig {
    @Produces
    public XmlMapper xmlMapper() {
        return XmlMapper.builder()
                .defaultUseWrapper(false)
                .addModule(new JavaTimeModule())
                .build();
    }
}
