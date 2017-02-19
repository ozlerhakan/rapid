package rapid.container;

import com.kodcu.rapid.path.Container;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import rapid.Config;

import javax.ws.rs.core.Application;

/**
 * Created by hakan on 17/02/2017.
 */
class ContainerConfig extends Config {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(Container.class);
    }

}
