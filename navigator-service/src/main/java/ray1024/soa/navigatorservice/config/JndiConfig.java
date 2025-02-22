package ray1024.soa.navigatorservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;
import ray1024.soa.navigatorservice.ejb.RemoteNavigatorService;

import javax.naming.NamingException;

@Component
public class JndiConfig {

    @Bean
    public JndiTemplate jndiTemplate() {
        return new JndiTemplate();
    }

    @Bean
    public RemoteNavigatorService remoteNavigatorService(JndiTemplate jndiTemplate) throws NamingException {
        String path = "ejb:/%s/%s!%s".formatted(
                "navigator-service-ejb-1.0-SNAPSHOT", "RemoteNavigatorService",
                RemoteNavigatorService.class.getName()
        );
        return jndiTemplate.lookup(path, RemoteNavigatorService.class);
    }

}
