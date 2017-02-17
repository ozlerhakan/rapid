package rapid.container;

import com.kodcu.rapid.path.Container;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import javax.ws.rs.core.Application;

/**
 * Created by hakan on 17/02/2017.
 */
public class ContainerConfig extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(Container.class);
    }

}
