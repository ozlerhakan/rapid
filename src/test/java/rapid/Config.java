package rapid;


import com.kodcu.rapid.util.Networking;
import org.glassfish.jersey.test.JerseyTest;

import javax.json.JsonObject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Created by hakan on 19/02/2017.
 */
public class Config extends JerseyTest {

    protected Response getResponse(WebTarget target) {
        return Networking.getResponse(target);
    }

    protected Response postResponse(WebTarget target, JsonObject content) {
        return Networking.postResponse(target, content);
    }

    protected Response postResponse(WebTarget target, String content) {
        return Networking.postResponse(target, content);
    }

    protected Response postResponse(WebTarget target) {
        return Networking.postResponse(target);
    }

    protected Response deleteResponse(WebTarget target) {
        return Networking.deleteResponse(target);
    }
}
