package rapid.image;

import org.junit.Test;

import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 16/02/2017.
 */
public class TestImageTag extends ImageConfig {

    @Test
    public void shouldTagImage() {
        final Response response = postResponse(target("images").path("ubuntu:latest").path("tag").queryParam("repo","ubuntu").queryParam("tag","test"));
        System.out.println(response);
        assertEquals(200, response.getStatus());
        final JsonStructure responseContent = response.readEntity(JsonStructure.class);
        System.out.println(responseContent);
        response.close();

    }

}