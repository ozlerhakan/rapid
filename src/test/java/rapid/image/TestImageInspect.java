package rapid.image;

import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestImageInspect extends ImageConfig {

    @Test
    public void shouldInspectImage() {
        final Response response = getResponse(target("images").path("ubuntu:latest").path("json"));
        System.out.println(response);
        assertEquals(200, response.getStatus());
        final JsonObject responseContent = response.readEntity(JsonObject.class);
        response.close();

    }

}