package rapid;

import org.eclipse.jetty.http.MimeTypes;
import org.glassfish.jersey.test.JerseyTest;

import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

/**
 * Created by hakan on 19/02/2017.
 */
public class Config extends JerseyTest {

    public Response getResponse(WebTarget target) {
        return target.request(String.valueOf(MimeTypes.Type.APPLICATION_JSON_UTF_8)).get();
    }

    public Response postResponse(WebTarget target, JsonObject content) {
        return target.request(String.valueOf(MimeTypes.Type.APPLICATION_JSON_UTF_8)).method("POST", Entity.entity(content, MediaType.APPLICATION_JSON));
    }

    public Response postResponse(WebTarget target, String content) {
        return target.request(String.valueOf(MimeTypes.Type.APPLICATION_JSON_UTF_8)).method("POST", Entity.entity(content, MediaType.APPLICATION_JSON));
    }

    public Response postResponse(WebTarget target) {
        return target.request(String.valueOf(MimeTypes.Type.APPLICATION_JSON_UTF_8)).method("POST");
    }

    public Response deleteResponse(WebTarget target){
        return target.request().method("DELETE");
    }
}
