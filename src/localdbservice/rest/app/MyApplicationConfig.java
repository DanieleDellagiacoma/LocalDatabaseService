package localdbservice.rest.app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/localdbservice")
public class MyApplicationConfig extends ResourceConfig {
    public MyApplicationConfig () {
        packages("localdbservice.rest");
    }
}