package rapid.image;

import com.kodcu.rapid.path.Image;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import rapid.Config;

import javax.ws.rs.core.Application;

/**
 * Created by hakan on 17/02/2017.
 */
class ImageConfig extends Config {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(Image.class);
    }

}
